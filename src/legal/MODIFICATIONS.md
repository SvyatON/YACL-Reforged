# Modification notice

YACL Reforged 3.6.6+1.20-1.20.1 is an unofficial modified version of YetAnotherConfigLib. Maintainer: SvyatON. Source snapshot date: **2026-10-14**. Original author: isXander and upstream contributors. Original license and attribution notices are retained.

The port adapts Forge entrypoints, platform access, reload/render compatibility, access transformers, loader metadata, dependency packaging and branding. This standalone source snapshot keeps only the build profile for this release. The shared JAR preserves its full Minecraft compatibility range. Unused Modstitch, Fabric/NeoForge metadata and demonstration/test files are omitted. Required production sources, dependency sources/build references and legal texts remain available.

Upstream revision: `ae66e4c77c13e4e153da5afb9271ed59a359589e` (YACL 3.6.6). The following existing upstream files differ in this snapshot; new files supply Forge metadata/access, source packaging and the replacement logo.

| Modified file | Maintainer | Snapshot date |
| --- | --- | --- |
| `build.gradle.kts` | SvyatON | 2026-10-14 |
| `gradle.properties` | SvyatON | 2026-10-14 |
| `settings.gradle.kts` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/config/GsonConfigInstance.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/config/v2/impl/serializer/GsonConfigSerializer.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/AbstractWidget.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/ElementListWidgetExt.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/OptionDescriptionWidget.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/OptionListWidget.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/YACLScreen.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/YACLTooltip.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/controllers/ColorPickerWidget.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/controllers/dropdown/ItemControllerElement.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/controllers/slider/SliderControllerElement.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/image/YACLImageReloadListener.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/tab/ListHolderWidget.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/tab/ScrollableNavigationBar.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/utils/GuiUtils.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/utils/ItemRegistryHelper.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/utils/MiscUtil.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/utils/YACLRenderHelper.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/mixin/AbstractSelectionListAccessor.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/mixin/ContainerEventHandlerMixin.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/platform/YACLPlatform.java` | SvyatON | 2026-10-14 |
| `src/main/resources/META-INF/accesstransformer.cfg` | SvyatON | 2026-10-14 |
| `src/main/resources/yacl.mixins.json` | SvyatON | 2026-10-14 |
| `src/main/templates/META-INF/mods.toml` | SvyatON | 2026-10-14 |
| `stonecutter.gradle.kts` | SvyatON | 2026-10-14 |
| `versions/current` | SvyatON | 2026-10-14 |
| `versions/1.20-forge/gradle.properties` | SvyatON | 2026-10-14 |

See README.md for the exact build command and corresponding source tag. This notice is included in binary and sources JARs under META-INF.
