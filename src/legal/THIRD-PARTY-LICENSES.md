# Third-party licenses

YACL is LGPL-3.0-or-later. Dependencies below retain their separate licenses; they are not relicensed as YACL. Their original source notices are retained. Legal texts accompany every YACL binary and standard sources JAR under `META-INF/licenses/`.

| Component | Version | License and notices | Upstream |
| --- | --- | --- | --- |
| TwelveMonkeys common-lang, common-io, common-image, imageio-core, imageio-metadata, imageio-webp | 3.12.0 | BSD-3-Clause; Apache-origin IndexImage code also published by ASF under Apache-2.0, with the historical Apache-1.1 notice retained; `TwelveMonkeys-BSD-3-Clause.txt`, `TwelveMonkeys-source-notices.txt`, `Apache-1.1.txt`, `Apache-2.0.txt`, `Batik-Apache-2.0-provenance.txt` | https://github.com/haraldk/TwelveMonkeys/tree/twelvemonkeys-3.12.0 |
| Quilt Parsers json and gson | 0.2.1 | Apache-2.0; `Apache-2.0.txt`, `Quilt-source-notices.txt`; includes Google and QuiltMC attribution | https://github.com/QuiltMC/parsers |
| JetBrains Java Annotations | 26.0.2 | Apache-2.0; `Apache-2.0.txt`, `JetBrains-source-notices.txt` | https://github.com/JetBrains/java-annotations |
| MixinExtras Forge and its nested common library, where packaged | 0.5.0 | MIT; `MixinExtras-MIT.txt`, original `LICENSE_MixinExtras` retained inside dependency JARs | https://github.com/LlamaLad7/MixinExtras |
| Apache Commons Lang, relocated inside MixinExtras | 3.3.2 | Apache-2.0; original license and NOTICE reproduced in `MixinExtras-commons-lang3-notices.txt` | https://github.com/apache/commons-lang |
| ANTLR runtime, relocated inside MixinExtras | 4.13.1 | BSD-3-Clause; full `ANTLR-BSD-3-Clause.txt` and original notices in `MixinExtras-antlr4-runtime-notices.txt` | https://github.com/antlr/antlr4/tree/4.13.1 |
| Gson, relocated inside MixinExtras | 2.11.0 | Apache-2.0; original notices in `MixinExtras-gson-notices.txt` | https://github.com/google/gson/tree/gson-parent-2.11.0 |
| Java SemVer, relocated inside MixinExtras | 0.10.2 | MIT; original notices in `MixinExtras-java-semver-notices.txt` | https://github.com/zafarkhaja/jsemver |
| Cloth Config dynamic-height list code retained by upstream in ElementListWidgetExt, 1.20.x and 1.21.x families | Original upstream adaptation | LGPL3; original source attribution retained, `LICENSE`, `GPL-3.0.txt` | https://github.com/shedaniel/cloth-config |
| Gradle wrapper, source distributions only | See each wrapper properties file | Apache-2.0; original `META-INF/LICENSE` retained inside wrapper JARs | https://github.com/gradle/gradle |

Annotations is supplied by Forge rather than nested in some older releases. MixinExtras is nested only where required by the target loader. These libraries are unmodified. Their matching published source JARs accompany the complete project source archive, with hashes and download origins in `gradle/bundled-library-sources.json`.

Minecraft, Forge, Kotlin, Gson and other compile-time or loader-provided libraries are resolved externally by the build or installed separately. They are not redistributed inside YACL unless listed above. Optional Kotlin Reforged is a separate work in release bundles: retain its own license, third-party notices and matching complete sources when distributing that companion. Its license does not replace YACL's license.

This product includes software developed by the Apache Software Foundation (http://www.apache.org/). The historical Batik Apache-1.1 notice is preserved verbatim in the TwelveMonkeys source notices; it is not silently replaced by Apache-2.0. TwelveMonkeys and MixinExtras remain separate, unmodified nested libraries with their original identities and licenses.

The Apache-origin IndexImage implementation was published with an unchanged source body under Apache-2.0 in Batik 1.6. This distribution relies on that documented compatible grant for the Apache-origin code while retaining the BSD terms for the TwelveMonkeys adaptation and all historical notices. The immutable upstream source references, exact file/body hashes and original Apache-2.0 header are provided in `src/legal/LICENSES/Batik-Apache-2.0-provenance.txt`.
