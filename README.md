# YACL Reforged

<img src="src/main/resources/yacl-reforged-256.png" alt="YACL Reforged" width="128" height="128">

A builder-based configuration library for Minecraft, unofficially ported to modern Forge versions.

[Download on Modrinth](https://modrinth.com/project/Uwg487V8) · [Source code](https://github.com/SvyatON/YACL-Reforged) · [Report a bug](https://github.com/SvyatON/YACL-Reforged/issues)

Original author: **isXander**. Forge port: **SvyatON**.

This unofficial port preserves the original YACL configuration API, `dev.isxander.yacl3` packages and `yet_another_config_lib_v3` resource namespace. Its Forge mod ID is `yacl_reforged`.

## Features

- Configuration categories, collapsible groups and custom controllers.
- Sliders, dropdowns, lists and a full color picker.
- Rich text descriptions and animated WebP previews.
- JSON, JSON5 and Codec serialization, automatic configuration screens and Kotlin DSL.
- Keyboard navigation and the original Minecraft GUI appearance.

## Supported versions

This repository builds Forge releases for Minecraft 1.20.x, 1.21.x and 26.x. Shared JARs cover the Minecraft ranges shown below.

| Minecraft | Release version | Corresponding source |
| --- | --- | --- |
| 26.1-26.1.2 | `3.9.7+26.1-26.1.2` | [F-3.9.7+26.1-26.1.2](https://github.com/SvyatON/YACL-Reforged/tree/F-3.9.7+26.1-26.1.2) |
| 26.2 | `3.9.7+26.2` | [F-3.9.7+26.2](https://github.com/SvyatON/YACL-Reforged/tree/F-3.9.7+26.2) |
| 26.3 | `3.9.7+26.3` | [F-3.9.7+26.3](https://github.com/SvyatON/YACL-Reforged/tree/F-3.9.7+26.3) |
| 1.21-1.21.1 | `3.8.2+1.21-1.21.1` | [F-3.8.2+1.21-1.21.1](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21-1.21.1) |
| 1.21.9-1.21.10 | `3.8.2+1.21.9-1.21.10` | [F-3.8.2+1.21.9-1.21.10](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21.9-1.21.10) |
| 1.21.11 | `3.8.2+1.21.11` | [F-3.8.2+1.21.11](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21.11) |
| 1.21.3 | `3.8.2+1.21.3` | [F-3.8.2+1.21.3](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21.3) |
| 1.21.4 | `3.8.2+1.21.4` | [F-3.8.2+1.21.4](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21.4) |
| 1.21.5 | `3.8.2+1.21.5` | [F-3.8.2+1.21.5](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21.5) |
| 1.21.6-1.21.8 | `3.8.2+1.21.6-1.21.8` | [F-3.8.2+1.21.6-1.21.8](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21.6-1.21.8) |
| 1.20-1.20.1 | `3.6.6+1.20-1.20.1` | [F-3.6.6+1.20-1.20.1](https://github.com/SvyatON/YACL-Reforged/tree/F-3.6.6+1.20-1.20.1) |
| 1.20.2 | `3.6.6+1.20.2` | [F-3.6.6+1.20.2](https://github.com/SvyatON/YACL-Reforged/tree/F-3.6.6+1.20.2) |
| 1.20.3-1.20.4 | `3.6.6+1.20.3-1.20.4` | [F-3.6.6+1.20.3-1.20.4](https://github.com/SvyatON/YACL-Reforged/tree/F-3.6.6+1.20.3-1.20.4) |
| 1.20.6 | `3.6.6+1.20.6` | [F-3.6.6+1.20.6](https://github.com/SvyatON/YACL-Reforged/tree/F-3.6.6+1.20.6) |

See [Forge compatibility](#forge-compatibility) for minimum loader versions and [installation](#installation) for choosing a release.

## Usage for developers

Declare `yacl_reforged` as your Forge dependency. Existing `dev.isxander.yacl3` imports and resource identifiers remain unchanged. See the [upstream API documentation](https://docs.isxander.dev/yet-another-config-lib) for configuration builders and controllers.

YACL embeds no Kotlin runtime and has no mandatory Kotlin dependency. Its Java API works on its own. Mods using the Kotlin DSL must provide a compatible external Kotlin runtime; see [installation](#installation).

## Building

Run Gradle with JDK 25. Java toolchains use Java 17 for Minecraft 1.20–1.20.4, Java 21 for 1.20.6 and 1.21.x, and Java 25 for 26.x.

```powershell
.\gradlew.bat :26.3-forge:buildAndCollect "-Ptargets=26.3" --configure-on-demand
.\gradlew.bat -p 1.21.x :1.21.11-forge:buildAndCollect "-Ptargets=1.21.11" --configure-on-demand
.\gradlew.bat -p 1.20.x :1.20-forge:buildAndCollect "-Ptargets=1.20" --configure-on-demand
```

On Linux and macOS, use `./gradlew` with the same arguments. Release JARs and their matching sources are collected in each source family's `build/releases` directory. The 1.20.x SRG remapping step also needs Python 3.11 or newer on `PATH`.

Collected release files are located in `build/releases/` for 26.x, `1.21.x/build/releases/` for 1.21.x, and `1.20.x/build/releases/` for 1.20.x. Install `yacl-reforged-<version>.jar`; `*-sources.jar` contains source code and does not belong in `mods`.

The project retains upstream Stonecutter preprocessing. `src` and `versions` contain the 26.x family; `1.21.x` contains 1.21.x; `1.20.x` contains 1.20.x. Each target’s `versions/<target>/gradle.properties` defines its Minecraft and Forge ranges and shared artifact version. The upstream demonstration mod remains in each family's `src/testmod`.

## Installation

Download the Forge JAR matching your Minecraft version from [Modrinth](https://modrinth.com/project/Uwg487V8) and place it in `mods`. Use at least the Forge version listed in [Forge compatibility](#forge-compatibility).

YACL is a library: an integrating mod opens its configuration screen. That mod must support Forge and declare the `yacl_reforged` dependency.

### Kotlin mods

YACL's Java API needs no Kotlin companion. If an integrating mod uses Kotlin or the Kotlin DSL, install the runtime it requires. The following companions have been tested with this port:

| Minecraft | Tested companion for Kotlin mods |
| --- | --- |
| 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4 | Official 4.12.0 |
| 1.20.6, 1.21, 1.21.1, 1.21.3, 1.21.4, 1.21.5 | Official 5.12.0 |
| 1.21.6, 1.21.7, 1.21.8, 1.21.9, 1.21.10, 1.21.11 | Kotlin Reforged 7.0.0+1.21.6-26.3 |
| 26.1, 26.1.1, 26.1.2, 26.2, 26.3 | Kotlin Reforged 7.0.0+1.21.6-26.3 |

Official downloads: [Kotlin for Forge 4.12.0](https://modrinth.com/mod/kotlin-for-forge/version/Zsh14XeQ), [Kotlin for Forge 5.12.0](https://modrinth.com/mod/kotlin-for-forge/version/uhJhCT7X). For modern Forge, use [Kotlin Reforged](https://modrinth.com/project/QXMi7yei), mod ID `kotlin_reforged`.

Install one matching runtime. Sources archives belong with source distributions, not in `mods`.

### Bug reports

Report problems at [GitHub Issues](https://github.com/SvyatON/YACL-Reforged/issues). Include Minecraft and Forge versions, the YACL JAR filename, the relevant log and steps to reproduce the problem.

## Forge compatibility

Use these minimum Forge versions or later patches for the corresponding Minecraft release. The latest column records the endpoint checked on 2026-10-11.

| Minecraft | Minimum Forge | Latest check target |
|---|---|---|
| 1.20 | 46.0.14 | 46.0.14 |
| 1.20.1 | 47.4.10 | 47.4.26 |
| 1.20.2 | 48.1.0 | 48.1.0 |
| 1.20.3 | 49.0.2 | 49.0.2 |
| 1.20.4 | 49.0.3 | 49.2.8 |
| 1.20.6 | 50.1.20 | 50.2.10 |
| 1.21 | 51.0.33 | 51.0.33 |
| 1.21.1 | 52.1.10 | 52.1.16 |
| 1.21.3 | 53.0.25 | 53.1.12 |
| 1.21.4 | 54.0.6 | 54.1.18 |
| 1.21.5 | 55.0.4 | 55.1.14 |
| 1.21.6 | 56.0.9 | 56.0.9 |
| 1.21.7 | 57.0.3 | 57.0.3 |
| 1.21.8 | 58.1.0 | 58.1.22 |
| 1.21.9 | 59.0.5 | 59.0.5 |
| 1.21.10 | 60.0.18 | 60.1.15 |
| 1.21.11 | 61.0.1 | 61.2.1 |
| 26.1 | 62.0.9 | 62.0.9 |
| 26.1.1 | 63.0.1 | 63.0.2 |
| 26.1.2 | 64.0.8 | 64.1.3 |
| 26.2 | 65.1.0 | 65.1.3 |
| 26.3 | 66.0.4 | 66.0.10 |

Shared Minecraft JARs use separate Maven ranges where Forge majors have different minimum patches. Higher patches are accepted by metadata; a future Forge API change may require an update.

The supported Minecraft profiles are defined by `versions/*/gradle.properties` in each source family.

## Source provenance

Upstream: [isXander/YetAnotherConfigLib](https://github.com/isXander/YetAnotherConfigLib). Original author: isXander. Forge port: SvyatON.

| Source family | Upstream revision | Upstream version |
| --- | --- | --- |
| Minecraft 26.x | `97f89e5bd0eff3811324680b6dec9e0e1009b8c0` | 3.9.7 |
| Minecraft 1.20.x | `ae66e4c77c13e4e153da5afb9271ed59a359589e` | 3.6.6 |
| Minecraft 1.21.x | `18f25e168bf7778024b103fbbfc0327bd913bb65` | 3.8.2 |

The port retains upstream Git history, library packages, API, translations and resource namespaces. It adapts Forge entrypoints, platform access, reload listeners, access transformers, rendering access, loader metadata and Jar-in-Jar packaging. Minecraft 1.20–1.20.4 releases use SRG mappings; later releases use official names.

Each source family retains its original demonstration mod. Kotlin libraries are compile-only dependencies; release JARs embed no Kotlin runtime and declare no mandatory Kotlin companion. The port uses its own orange gear logo.

Original copyright attribution and the LGPL-3.0-or-later license are preserved.

## License

YACL Reforged is distributed under [LGPL-3.0-or-later](LICENSE). Original author attribution and license are preserved.

Full GPL terms referenced by LGPL are provided in [src/legal/LICENSES/GPL-3.0.txt](src/legal/LICENSES/GPL-3.0.txt). See [NOTICE](src/legal/NOTICE), [dated modifications](src/legal/MODIFICATIONS.md), [third-party licenses](src/legal/THIRD-PARTY-LICENSES.md) and the [full legal texts](src/legal/LICENSES).

Legal texts and notices are retained as separate files so they can accompany source distributions and be included in release JARs under `META-INF`. Installation, compatibility, source provenance and release instructions are provided in this README.

## Publishing a release

Forge release tags use `F-<YACL version>+<Minecraft version or range>`, for example `F-3.9.7+26.3` or `F-3.8.2+1.21.6-1.21.8`. Each tag preserves the complete corresponding public source snapshot. The source links for current releases are listed in [Supported versions](#supported-versions).

YACL Reforged retains LGPL-3.0-or-later. Preserve the upstream author, copyright and license notices, the dated modification notice, and third-party notices. Present the project as an unofficial Forge port; do not imply upstream endorsement.

Build from the repository root using the commands in [Building](#building). Run `python gradle/package-project-sources.py` from the repository root after building. The archive contains all source families, resources, Gradle scripts/wrappers, target properties, legal documents and matching published sources of bundled dependencies. `release-artifacts.json` inside records the available binary versions and SHA-256 hashes. A standard `*-sources.jar` alone is not the complete buildable source distribution.

For every published binary:

1. Commit the exact corresponding public sources, resources and build/packaging scripts. Preserve them with a matching release tag whose name or format has been selected by the maintainer. Keep the matching sources downloadable without charge for as long as required by the applicable license. Never substitute newer or unrelated sources for an older binary.
2. Place a browsable link to that matching GitHub tag on the specific version page or changelog on Modrinth, CurseForge or another distribution platform. State the source family and Gradle target needed to build that binary. One tagged Stonecutter snapshot can correspond to multiple binaries when it contains the complete corresponding sources and build instructions for each target. A separate sources JAR/ZIP attachment is optional when this source-access method satisfies the applicable license. The complete project ZIP remains available as an alternative source distribution; a standard sources JAR by itself omits required build materials.
3. Publish README.md, LICENSE and the complete src/legal directory with the sources. Binary JARs already carry the applicable legal documents under META-INF.
4. Select LGPL-3.0-or-later on the project page, credit isXander and link the original repository. Keep homepage, source and issue links accurate. State the tested Minecraft and Forge ranges.
5. If distributing optional Kotlin Reforged alongside YACL, provide access to its exact matching complete sources under its applicable license and retain its own licenses and third-party notices. A matching public source tag can be used when that license permits it. Separate download links are preferable to repackaging a runtime into YACL.

The project uses replaceable JARs and imposes no further restriction on modifying the LGPL library, debugging modifications or rebuilding an interface-compatible replacement. No Minecraft game files, test installations, credentials or private tools belong in the source archive.

Before uploading to any hosting service, verify its current content rules separately from copyright licensing.

The original logo PNG is retained in `src/branding/yacl-reforged-original.png`; optimized runtime copies are 256 pixels.
