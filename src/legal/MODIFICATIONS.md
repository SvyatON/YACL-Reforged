# Modification notice

YACL Reforged 3.8.2+1.21.9-1.21.10 is an unofficial modified version of YetAnotherConfigLib. Maintainer: SvyatON. Source snapshot date: **2026-10-14**. Original author: isXander and upstream contributors. Original license and attribution notices are retained.

The port adapts Forge entrypoints, platform access, reload/render compatibility, access transformers, loader metadata, dependency packaging and branding. This standalone source snapshot keeps only the build profile for this release. The shared JAR preserves its full Minecraft compatibility range. Unused Modstitch, Fabric/NeoForge metadata and demonstration/test files are omitted. Required production sources, dependency sources/build references and legal texts remain available.

Upstream revision: `18f25e168bf7778024b103fbbfc0327bd913bb65` (YACL 3.8.2). The following existing upstream files differ in this snapshot; new files supply Forge metadata/access, source packaging and the replacement logo.

| Modified file | Maintainer | Snapshot date |
| --- | --- | --- |
| `build.gradle.kts` | SvyatON | 2026-10-14 |
| `gradle.properties` | SvyatON | 2026-10-14 |
| `settings.gradle.kts` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/gui/image/YACLImageReloadListener.java` | SvyatON | 2026-10-14 |
| `src/main/java/dev/isxander/yacl3/platform/YACLPlatform.java` | SvyatON | 2026-10-14 |
| `src/main/resources/yacl.mixins.json` | SvyatON | 2026-10-14 |
| `src/main/templates/META-INF/mods.toml` | SvyatON | 2026-10-14 |
| `stonecutter.gradle.kts` | SvyatON | 2026-10-14 |
| `versions/current` | SvyatON | 2026-10-14 |
| `versions/1.21.9-forge/gradle.properties` | SvyatON | 2026-10-14 |

See README.md for the exact build command and corresponding source tag. This notice is included in binary and sources JARs under META-INF.
