import argparse
import gzip
from pathlib import Path
import re
import zipfile


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('input')
    parser.add_argument('output')
    parser.add_argument('mappings')
    parser.add_argument('--reverse', action='store_true')
    args = parser.parse_args()
    data = Path(args.mappings).read_bytes()
    if data[:2] == b'\x1f\x8b': data = gzip.decompress(data)
    members = {}
    owner = None
    for line in data.decode().splitlines():
        if line.startswith('tsrg2 '): continue
        fields = line.split()
        if not fields: continue
        if not line.startswith('\t'):
            owner = fields[1] if args.reverse else fields[0]
        elif not line.startswith('\t\t'):
            if len(fields) == 2:
                before, after = fields
                descriptor = ''
            elif len(fields) >= 3 and fields[1].startswith('('):
                before, descriptor, after = fields[:3]
            else: continue
            if args.reverse: before, after = after, before
            members[(owner.replace('/', '.'), before, descriptor)] = after
    target = Path(args.output)
    target.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(args.input) as source, zipfile.ZipFile(target, 'w', zipfile.ZIP_DEFLATED) as output:
        for info in source.infolist():
            value = source.read(info.filename)
            if info.filename == 'META-INF/accesstransformer.cfg':
                lines = []
                for line in value.decode().splitlines():
                    fields = line.split()
                    if len(fields) == 3:
                        match = re.fullmatch(r'([^\(]+)(\(.*)?', fields[2])
                        name, descriptor = match.group(1), match.group(2) or ''
                        mapped = members.get((fields[1], name, descriptor), name)
                        fields[2] = mapped + descriptor
                        line = ' '.join(fields)
                    lines.append(line)
                value = ('\n'.join(lines) + '\n').encode()
            info.date_time = (1980, 1, 1, 0, 0, 0)
            output.writestr(info, value)


if __name__ == '__main__':
    main()
