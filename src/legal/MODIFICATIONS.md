# Modification notice

YACL Reforged is an unofficial modified version of YetAnotherConfigLib. Maintainer: SvyatON. Snapshot date: **2026-10-14**. Original author: isXander and upstream contributors. License: LGPL-3.0-or-later; original license and attribution notices are retained.

The port changes Forge entrypoints, platform access, reload/render compatibility, access transformers, loader/version metadata, dependency packaging, build configuration and branding. The source families retain their upstream API and translations. This notice records the present modified snapshot, rather than claiming every change was first made on this date.

The following existing upstream files differ in this snapshot. New files provide Forge access transformers, metadata templates, build targets, legal/source packaging, documentation and the supplied replacement logo. Removed files comprise unused upstream release workflows, unsupported loader targets, the old logo and entrypoint/metadata files replaced by generated equivalents. The source-only cleanup also removes unused Modstitch access-widener files, Fabric/NeoForge templates and mixin configurations, the upstream Crowdin/changelog files, and the demonstration mod. Test assets and run automation are maintained separately from the published build project.

## . — upstream `97f89e5bd0eff3811324680b6dec9e0e1009b8c0`

| Modified file | Maintainer | Snapshot date |
| --- | --- | --- |
| `.github/workflows/gradle.yml` | SvyatON | 2026-10-14 |
| `.gitignore` | SvyatON | 2026-10-14 |
| `README.md` | SvyatON | 2026-10-14 |
| `build.gradle.kts` | SvyatON | 2026-10-14 |
| `gradle.properties` | SvyatON | 2026-10-14 |
| `settings.gradle.kts` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/platform/PlatformEntrypoint.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/platform/YACLPlatform.java` | SvyatON | 2026-10-14 |
| `src/main/resources/yacl.mixins.json` | SvyatON | 2026-10-14 |
| `src/main/templates/META-INF/mods.toml` | SvyatON | 2026-10-14 |
| `stonecutter.gradle.kts` | SvyatON | 2026-10-14 |
| `versions/current` | SvyatON | 2026-10-14 |
| `versions/26.1-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `versions/26.1.1-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `versions/26.1.2-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `versions/26.2-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `versions/26.3-forge/gradle.properties` | SvyatON | 2026-10-14 |

## 1.21.x — upstream `18f25e168bf7778024b103fbbfc0327bd913bb65`

| Modified file | Maintainer | Snapshot date |
| --- | --- | --- |
| `1.21.x/build.gradle.kts` | SvyatON | 2026-10-14 |
| `1.21.x/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/settings.gradle.kts` | SvyatON | 2026-10-14 |
| `1.21.x/src/main/java/dev/isxander/yacl3/gui/image/YACLImageReloadListener.java` | SvyatON | 2026-10-14 |
| `1.21.x/src/main/java/dev/isxander/yacl3/platform/YACLPlatform.java` | SvyatON | 2026-10-14 |
| `1.21.x/src/main/resources/yacl.mixins.json` | SvyatON | 2026-10-14 |
| `1.21.x/src/main/templates/META-INF/mods.toml` | SvyatON | 2026-10-14 |
| `1.21.x/stonecutter.gradle.kts` | SvyatON | 2026-10-14 |
| `1.21.x/versions/current` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.1-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.10-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.11-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.3-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.4-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.5-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.6-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.7-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.8-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.21.x/versions/1.21.9-forge/gradle.properties` | SvyatON | 2026-10-14 |

## 1.20.x — upstream `ae66e4c77c13e4e153da5afb9271ed59a359589e`

| Modified file | Maintainer | Snapshot date |
| --- | --- | --- |
| `1.20.x/build.gradle.kts` | SvyatON | 2026-10-14 |
| `1.20.x/gradle.properties` | SvyatON | 2026-10-14 |
| `1.20.x/settings.gradle.kts` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/config/GsonConfigInstance.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/config/v2/impl/serializer/GsonConfigSerializer.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/AbstractWidget.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/ElementListWidgetExt.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/OptionDescriptionWidget.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/OptionListWidget.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/YACLScreen.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/YACLTooltip.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/controllers/ColorPickerWidget.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/controllers/dropdown/ItemControllerElement.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/controllers/slider/SliderControllerElement.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/image/YACLImageReloadListener.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/tab/ListHolderWidget.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/tab/ScrollableNavigationBar.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/utils/GuiUtils.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/utils/ItemRegistryHelper.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/utils/MiscUtil.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/gui/utils/YACLRenderHelper.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/mixin/AbstractSelectionListAccessor.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/mixin/ContainerEventHandlerMixin.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/java/dev/isxander/yacl3/platform/YACLPlatform.java` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/resources/META-INF/accesstransformer.cfg` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/resources/yacl.mixins.json` | SvyatON | 2026-10-14 |
| `1.20.x/src/main/templates/META-INF/mods.toml` | SvyatON | 2026-10-14 |
| `1.20.x/stonecutter.gradle.kts` | SvyatON | 2026-10-14 |
| `1.20.x/versions/1.20.1-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.20.x/versions/current` | SvyatON | 2026-10-14 |
| `1.20.x/versions/1.20-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.20.x/versions/1.20.2-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.20.x/versions/1.20.3-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.20.x/versions/1.20.4-forge/gradle.properties` | SvyatON | 2026-10-14 |
| `1.20.x/versions/1.20.6-forge/gradle.properties` | SvyatON | 2026-10-14 |

This notice accompanies source and binary distributions in `META-INF/MODIFICATIONS.md`. Required third-party notices remain unchanged and are reproduced under `src/legal/LICENSES/`. Complete project sources include every source family and the build scripts; see the Publishing a release section in `README.md`.
