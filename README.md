# YACL Reforged

<img src="src/main/resources/yacl-reforged-256.png" alt="YACL Reforged" width="96" align="right">

A builder-based configuration library for Minecraft, unofficially ported to modern Forge versions.

[Modrinth](https://modrinth.com/project/Uwg487V8) · [Issues](https://github.com/SvyatON/YACL-Reforged/issues) · [API documentation](https://docs.isxander.dev/yet-another-config-lib)

Original author: **[isXander](https://github.com/isXander/YetAnotherConfigLib)**. Forge port: **SvyatON**.

Mod ID: `yacl_reforged`. The original `dev.isxander.yacl3` API and resource namespace are preserved.

## Versions

| Minecraft | YACL | Source directory |
| --- | --- | --- |
| 1.20–1.20.4, 1.20.6 | 3.6.6 | `1.20.x/` |
| 1.21–1.21.1, 1.21.3–1.21.11 | 3.8.2 | `1.21.x/` |
| 26.1–26.1.2, 26.2, 26.3 | 3.9.7 | Repository root |

Download the matching Forge JAR from Modrinth and place it in `mods`. Minecraft and minimum Forge ranges are defined in each family's `versions/*/gradle.properties`. YACL has no mandatory Kotlin dependency; Kotlin mods must provide their own compatible runtime.

## Building

Run Gradle with JDK 25. Java toolchains use 17 for 1.20–1.20.4, 21 for 1.20.6 and 1.21.x, and 25 for 26.x. The 1.20.x SRG remapping step also requires Python 3.11+.

```powershell
.\gradlew.bat :26.3-forge:buildAndCollect "-Ptargets=26.3" --configure-on-demand
.\gradlew.bat -p 1.21.x :1.21.11-forge:buildAndCollect "-Ptargets=1.21.11" --configure-on-demand
.\gradlew.bat -p 1.20.x :1.20-forge:buildAndCollect "-Ptargets=1.20" --configure-on-demand
```

Use `./gradlew` on Linux/macOS. Change the target to select another version. JARs are collected in the selected family's `build/releases/`; install `yacl-reforged-<version>.jar`.

Each [release tag](https://github.com/SvyatON/YACL-Reforged/tags) (`F-<YACL version>+<Minecraft version or range>`) contains a standalone project with its own build instructions. A complete source archive can be created with `python gradle/package-project-sources.py`.

## License

[LGPL-3.0-or-later](LICENSE). See [author notices](src/legal/NOTICE), [modifications](src/legal/MODIFICATIONS.md), [third-party notices](src/legal/THIRD-PARTY-LICENSES.md) and [full license texts](src/legal/LICENSES). Link the matching source tag from each published binary's version page.
