from concurrent.futures import ThreadPoolExecutor
import hashlib
import json
from pathlib import Path
import subprocess
import urllib.request
import zipfile

ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / 'build/releases/YACL-Reforged-project-sources.zip'
EXCLUDED = {'.git', '.tools', '.gradle', '.kotlin', '.idea', '.architectury-transformer', 'build', 'run', '__pycache__', 'releases', 'dependencies'}


def public_files():
    try:
        repository = subprocess.check_output(['git', 'rev-parse', '--show-toplevel'], cwd=ROOT, stderr=subprocess.DEVNULL).decode().strip()
        if Path(repository).resolve() != ROOT.resolve():
            raise ValueError('Source directory is not the Git checkout root')
        names = subprocess.check_output(['git', 'ls-files', '--cached', '--others', '--exclude-standard', '-z'], cwd=ROOT, stderr=subprocess.DEVNULL).decode('utf-8').split('\0')
        paths = [ROOT / name for name in sorted(set(names)) if name]
    except (OSError, ValueError, subprocess.CalledProcessError):
        paths = []
        pending = [ROOT]
        while pending:
            directory = pending.pop()
            for path in directory.iterdir():
                if path.name in EXCLUDED:
                    continue
                if path.is_dir():
                    pending.append(path)
                elif path.is_file():
                    paths.append(path)
    result = []
    for path in paths:
        name = path.relative_to(ROOT)
        if not path.is_file() or any(part in EXCLUDED for part in name.parts):
            continue
        if name.as_posix() == 'release-artifacts.json':
            continue
        if path.suffix in {'.zip', '.class', '.pyc', '.log', '.tmp', '.bak'}:
            continue
        if path.suffix == '.jar' and name.as_posix() not in {'gradle/wrapper/gradle-wrapper.jar', '1.20.x/gradle/wrapper/gradle-wrapper.jar', '1.21.x/gradle/wrapper/gradle-wrapper.jar'}:
            raise ValueError(f'Unexpected JAR in public sources: {name}')
        result.append(path)
    return sorted(result)


def dependency_source(entry):
    request = urllib.request.Request(entry['url'], headers={'User-Agent': 'YACL-Reforged/source-packager'})
    with urllib.request.urlopen(request, timeout=45) as response:
        data = response.read()
    if hashlib.sha256(data).hexdigest() != entry['sha256']:
        raise ValueError(f"Dependency source checksum mismatch: {entry['file']}")
    return entry['file'], data


def release_profiles():
    entries = []
    for family in ('.', '1.20.x', '1.21.x'):
        directory = ROOT / family
        common = dict(line.split('=', 1) for line in (directory / 'gradle.properties').read_text().splitlines() if '=' in line and not line.lstrip().startswith('#'))
        for path in sorted((directory / 'versions').glob('*/gradle.properties')):
            properties = dict(line.split('=', 1) for line in path.read_text().splitlines() if '=' in line and not line.lstrip().startswith('#'))
            entries.append({'project': family, 'target': path.parent.name, 'minecraft': properties['mcVersion'], 'version': common['modVersion'] + '+' + properties.get('artifact.minecraft', properties['mcVersion']), 'artifactTarget': properties.get('artifact.target', path.parent.name)})
    return entries


def main():
    OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    files = public_files()
    expected = {'README.md', 'LICENSE', 'src/legal/NOTICE', 'src/legal/MODIFICATIONS.md', 'src/legal/LICENSES/GPL-3.0.txt', 'build.gradle.kts', '1.20.x/build.gradle.kts', '1.21.x/build.gradle.kts', 'gradle/package-project-sources.py'}
    actual = {path.relative_to(ROOT).as_posix() for path in files}
    if not expected <= actual:
        raise ValueError(f'Missing complete-source files: {expected - actual}')
    entries = release_profiles()
    releases = []
    for version, entry in sorted({entry['version']: entry for entry in entries}.items()):
        path = ROOT / entry['project'] / 'build/releases' / f'yacl-reforged-{version}.jar'
        if path.is_file():
            releases.append({'version': version, 'binary': path.name, 'sha256': hashlib.sha256(path.read_bytes()).hexdigest(), 'project': entry['project'], 'target': entry.get('artifactTarget', entry['target'])})
    libraries = json.loads((ROOT / 'gradle/bundled-library-sources.json').read_text())
    with ThreadPoolExecutor(max_workers=6) as pool:
        dependencies = list(pool.map(dependency_source, libraries))
    with zipfile.ZipFile(OUTPUT, 'w', zipfile.ZIP_DEFLATED) as archive:
        for path in files:
            archive.write(path, path.relative_to(ROOT).as_posix())
        for name, data in dependencies:
            archive.writestr('dependencies/sources/' + name, data)
        archive.writestr('release-artifacts.json', json.dumps(releases, indent=2) + '\n')
    with zipfile.ZipFile(OUTPUT) as archive:
        if archive.testzip() is not None:
            raise ValueError('Source archive is corrupt')
    print(f'{OUTPUT}: {len(files)} project files, {len(dependencies)} dependency source archives, {len(releases)} matching binaries')


if __name__ == '__main__':
    main()
