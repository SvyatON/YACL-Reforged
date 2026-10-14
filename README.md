# YACL Reforged

<img src="src/main/resources/yacl-reforged-256.png" alt="YACL Reforged" width="128" height="128">

A builder-based configuration library for Minecraft, unofficially ported to modern Forge versions.

[Download on Modrinth](https://modrinth.com/project/Uwg487V8) · [Source code](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21.3) · [Report a bug](https://github.com/SvyatON/YACL-Reforged/issues)

Original author: **isXander**. Forge port: **SvyatON**.

This source project builds **3.8.2+1.21.3** for Minecraft **1.21.3**, Forge only. Its Forge mod ID is `yacl_reforged`. Existing `dev.isxander.yacl3` imports and the `yet_another_config_lib_v3` resource namespace are preserved. The single JAR covers the declared Minecraft range `[1.21.3]` and accepts Forge range `[53.0.25,)`.

## Features

- Configuration categories, collapsible groups and custom controllers.
- Sliders, dropdowns, lists and a full color picker.
- Rich text descriptions and animated WebP previews.
- JSON, JSON5 and Codec serialization, automatic configuration screens and Kotlin DSL.
- Keyboard navigation and the original Minecraft GUI appearance.

## Building

Run Gradle with JDK 25. The Java toolchain for this release uses Java 21.

```powershell
.\gradlew.bat :1.21.3-forge:buildAndCollect --configure-on-demand
```

On Linux and macOS, use `./gradlew` with the same arguments. The single build profile is `versions/1.21.3-forge`. Stonecutter prepares its sources; compatible Minecraft versions are covered by the ranges in `versions/1.21.3-forge/gradle.properties`.

Install `build/releases/yacl-reforged-3.8.2+1.21.3.jar`. The matching `*-sources.jar` is for source distribution and does not belong in `mods`.

## Installation

Place the release JAR in `mods` with a compatible Forge installation. YACL is a library; an integrating mod opens its configuration screen. Its Java API needs no Kotlin runtime. Mods using Kotlin or the Kotlin DSL must provide their own compatible external runtime.

## Source provenance

This release is based on [YACL 3.8.2](https://github.com/isXander/YetAnotherConfigLib/tree/18f25e168bf7778024b103fbbfc0327bd913bb65). It preserves the upstream configuration API and translations while adapting Forge entrypoints, loader metadata, rendering/reload access, access transformers and dependency packaging. Other Minecraft build profiles and the upstream demonstration mod are not part of this source snapshot.

## License and corresponding source

YACL Reforged retains [LGPL-3.0-or-later](LICENSE). See [NOTICE](src/legal/NOTICE), [dated modifications](src/legal/MODIFICATIONS.md), [third-party licenses](src/legal/THIRD-PARTY-LICENSES.md) and the [full legal texts](src/legal/LICENSES).

This tag, [F-3.8.2+1.21.3](https://github.com/SvyatON/YACL-Reforged/tree/F-3.8.2+1.21.3), contains the complete corresponding source project for this release: source code, resources, Gradle wrapper/build scripts, dependency source references and legal notices. Link this exact tag from the particular binary version page or changelog. A separate source ZIP/JAR attachment is optional when that source-access method satisfies the applicable license. To create a complete local project source archive, run `python gradle/package-project-sources.py` after building.
