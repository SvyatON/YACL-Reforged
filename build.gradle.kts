import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm")
    id("dev.kikugie.stonecutter")
    id("net.minecraftforge.gradle")
    id("net.minecraftforge.jarjar")
    id("net.minecraftforge.renamer")
}

val mcVersion = property("mcVersion").toString()
val targetJava = if (stonecutter.eval(mcVersion, "<1.20.5")) 17 else 21
val srgRuntime = targetJava == 17
if (stonecutter.eval(mcVersion, "<1.21.1")) {
    configurations.configureEach {
        resolutionStrategy.force("net.sf.jopt-simple:jopt-simple:5.0.4")
    }
}
val releaseMinecraft = findProperty("artifact.minecraft") ?: mcVersion
val releaseVersion = "${property("modVersion")}+$releaseMinecraft"
version = releaseVersion
group = "dev.svyaton"
base.archivesName = "yacl-reforged"
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJava)
    withSourcesJar()
}
stonecutter {
    constants {
        put("fabric", false)
        put("neoforge", false)
        put("forge", true)
        put("forgelike", true)
        put("forge-like", true)
        put("controlify", false)
        put("mod-menu", false)
    }
    replacements {
        string {
            direction = eval(current.version, ">=1.21.5")
            replace("new ClickEvent(ClickEvent.Action.OPEN_URL, \"https://isxander.dev\")", "new ClickEvent.OpenUrl(URI.create(\"https://isxander.dev\"))")
        }
        string {
            direction = eval(current.version, ">=1.21.11")
            replace("ResourceLocation", "Identifier")
        }
        string {
            direction = eval(current.version, ">=1.21.11")
            replace("import net.minecraft.Util;", "import net.minecraft.util.Util;")
        }
    }
}
val forgeEntrypoint = tasks.register("generateForgeEntrypoint") {
    val file = layout.buildDirectory.file("generated/java/dev/isxander/yacl3/platform/PlatformEntrypoint.java")
    inputs.property("minecraft", mcVersion)
    outputs.file(file)
    doLast {
        val modernBus = stonecutter.eval(mcVersion, ">=1.21.9")
        val defaultConstructor = if (stonecutter.eval(mcVersion, "<1.21.2")) "public PlatformEntrypoint() { this(FMLJavaModLoadingContext.get()); }\n                    " else ""
        val listener = when {
            modernBus -> "RegisterClientReloadListenersEvent.BUS.addListener"
            stonecutter.eval(mcVersion, ">=1.21.6") -> "RegisterClientReloadListenersEvent.getBus(context.getModBusGroup()).addListener"
            else -> "context.getModEventBus().<RegisterClientReloadListenersEvent>addListener"
        }
        file.get().asFile.apply {
            parentFile.mkdirs()
            writeText("""
                package dev.isxander.yacl3.platform;
                import net.minecraftforge.api.distmarker.Dist;
                import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
                import net.minecraftforge.fml.common.Mod;
                import net.minecraftforge.fml.loading.FMLEnvironment;
                import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
                import dev.isxander.yacl3.gui.image.YACLImageReloadListener;
                @Mod("yacl_reforged")
                public class PlatformEntrypoint {
                    ${defaultConstructor}public PlatformEntrypoint(FMLJavaModLoadingContext context) {
                        if (FMLEnvironment.dist == Dist.CLIENT) Client.initialize(context);
                    }
                    private static final class Client {
                        private static void initialize(FMLJavaModLoadingContext context) {
                            YACLConfig.HANDLER.load();
                            $listener(event -> event.registerReloadListener(new YACLImageReloadListener()));
                        }
                    }
                }
            """.trimIndent())
        }
    }
}
sourceSets.main { java.srcDir(layout.buildDirectory.dir("generated/java")) }
minecraft {
    mappings("official", mcVersion)
    accessTransformers.from(rootProject.file("src/main/resources/META-INF/accesstransformer.cfg"))
    runs {
        configureEach {
            workingDir = layout.projectDirectory.dir("run")
        }
        register("client")
        register("server") { args("--nogui") }
    }
}
repositories {
    minecraft.mavenizer(this)
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
    exclusiveContent {
        forRepository { maven("https://maven.quiltmc.org/repository/release") }
        filter { includeGroupAndSubgroups("org.quiltmc") }
    }
    mavenCentral()
}
jarJar.register { archiveClassifier = "" }

dependencies {
    compileOnly("org.jetbrains:annotations:26.0.2")
    if (srgRuntime) {
        annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    }
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.0")
    implementation(minecraft.dependency(project.name, "net.minecraftforge:forge:$mcVersion-${property("deps.forge")}"))
    val bundled = listOf(
        "org.jetbrains:annotations:26.0.2",
        "com.twelvemonkeys.imageio:imageio-core:${property("deps.imageio")}",
        "com.twelvemonkeys.imageio:imageio-webp:${property("deps.imageio")}",
        "com.twelvemonkeys.imageio:imageio-metadata:${property("deps.imageio")}",
        "com.twelvemonkeys.common:common-lang:${property("deps.imageio")}",
        "com.twelvemonkeys.common:common-io:${property("deps.imageio")}",
        "com.twelvemonkeys.common:common-image:${property("deps.imageio")}",
        "org.quiltmc.parsers:json:${property("deps.quiltParsers")}",
        "org.quiltmc.parsers:gson:${property("deps.quiltParsers")}",
        "org.jetbrains.kotlin:kotlin-stdlib:2.3.0",
        "org.jetbrains.kotlin:kotlin-reflect:2.3.0",
        "io.github.llamalad7:mixinextras-forge:0.5.0"
    )
    bundled.filterNot { srgRuntime && it.startsWith("org.jetbrains:annotations:") }.forEach {
        if (it.startsWith("org.jetbrains.kotlin:")) {
            compileOnlyApi(it)
        } else {
            api(it)
            "jarJar"(it)
        }
    }
}
val metadata = mapOf(
    "mod_id" to property("modId"), "mod_version" to releaseVersion,
    "mod_name" to property("modName"), "mod_author" to property("modAuthors"),
    "mod_description" to property("modDescription"), "mod_license" to "LGPL-3.0-or-later",
    "mc" to property("meta.mcDep"),
    "loaderVersion" to property("meta.loaderDep"),
    "forgeVersion" to property("meta.forgeDep"),
)
tasks.processResources {
    inputs.property("srgRuntime", srgRuntime)
    if (!srgRuntime) doLast {
        val config = JsonSlurper().parse(rootProject.file("src/main/resources/yacl.mixins.json")) as MutableMap<String, Any>
        config.remove("refmap")
        destinationDir.resolve("yacl.mixins.json").writeText(JsonOutput.prettyPrint(JsonOutput.toJson(config)))
    }
    inputs.properties(metadata)
    inputs.property("resourcePackName", metadata.getValue("mod_name"))
    from(rootProject.file("src/main/templates/META-INF/mods.toml")) { into("META-INF"); expand(metadata) }
    filesMatching("pack.mcmeta") { expand(mapOf("name" to metadata.getValue("mod_name"))) }
    exclude("yacl-fabric.mixins.json", "fabric.mod.json", "META-INF/neoforge.mods.toml", "yacl.accesswidener")
}
tasks.withType<JavaCompile>().configureEach {
    dependsOn("stonecutterGenerate", forgeEntrypoint)
    options.encoding = "UTF-8"
    if (!srgRuntime) options.compilerArgs.add("-proc:none")
}
tasks.withType<KotlinCompile>().configureEach {
    dependsOn("stonecutterGenerate", forgeEntrypoint)
    compilerOptions.jvmTarget = JvmTarget.fromTarget(targetJava.toString())
}
tasks.jar {
    archiveClassifier = "slim"
    manifest.attributes("MixinConfigs" to "yacl.mixins.json")
    from(rootProject.file("LICENSE")) { into("META-INF") }
    from(listOf(rootProject.file("src/legal/NOTICE"), rootProject.file("src/legal/MODIFICATIONS.md"), rootProject.file("src/legal/THIRD-PARTY-LICENSES.md"))) { into("META-INF") }
    from(rootProject.file("src/legal/LICENSES")) { into("META-INF/licenses") }
}
tasks.named<Jar>("sourcesJar") {
    from(rootProject.file("LICENSE")) { into("META-INF") }
    from(listOf(rootProject.file("src/legal/NOTICE"), rootProject.file("src/legal/MODIFICATIONS.md"), rootProject.file("src/legal/THIRD-PARTY-LICENSES.md"))) { into("META-INF") }
    from(rootProject.file("src/legal/LICENSES")) { into("META-INF/licenses") }
}
val artifactTarget = findProperty("artifact.target")?.toString() ?: project.name
val releaseJar = if (srgRuntime) {
    renamer.mappings(minecraft.getDependency(project.name).toSrg)
    renamer.enableMixinRefmaps {
        config("yacl.mixins.json")
        refMap.set("yet_another_config_lib_v3.refmap.json")
        source(sourceSets.main.get())
    }
    tasks.named("mergeMixinMappings") { dependsOn(tasks.named("compileJava")) }
    val devMappings = renamer.convert("devMixinMappings") {
        map.setFrom(renamer.mappings)
        reverse.set(true)
        format.set("srg")
        output.set(layout.buildDirectory.file("mappings/devMixinMappings.srg"))
    }
    minecraft.runs.configureEach {
        systemProperty("mixin.env.remapRefMap", "true")
        systemProperty("mixin.env.refMapRemappingFile", layout.buildDirectory.file("mappings/devMixinMappings.srg").get().asFile.absolutePath)
        environment("MOD_CLASSES", "")
    }
    tasks.matching { it.name == "runClient" || it.name == "runServer" }.configureEach { dependsOn(devMappings) }
    tasks.jar {
        from(sourceSets.main.get().extensions.extraProperties.get("refMapFile")!!) { rename { "yet_another_config_lib_v3.refmap.json" } }
    }
    val renamed = renamer.classes("reobfJarJar", tasks.named<Jar>("jarJar")) {
        map.setFrom(renamer.mixin.generatedMappings.flatMap { it.output })
        output.set(layout.buildDirectory.file("reobf/raw/yacl-reforged-$releaseVersion.jar"))
    }
    val packaged = layout.buildDirectory.file("reobf/yacl-reforged-$releaseVersion.jar")
    val transform = tasks.register<Exec>("reobfAccessTransformers") {
        dependsOn(renamed)
        inputs.file(renamed.flatMap { it.output })
        inputs.file(minecraft.getDependency(project.name).toSrgFile)
        inputs.file(rootProject.file("gradle/remap-access-transformers.py"))
        outputs.file(packaged)
        doFirst {
            commandLine("python", rootProject.file("gradle/remap-access-transformers.py"), renamed.get().output.get().asFile, packaged.get().asFile, minecraft.getDependency(project.name).toSrgFile.get())
        }
    }
    transform.map { packaged.get() }
} else {
    tasks.named<Jar>("jarJar").flatMap { it.archiveFile }
}
if (artifactTarget == project.name) {
    tasks.register<Copy>("buildAndCollect") {
        dependsOn("jarJar", "sourcesJar")
        from(releaseJar)
        from(tasks.named<Jar>("sourcesJar").flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("releases"))
    }
} else {
    tasks.register("buildAndCollect") {
        dependsOn(":$artifactTarget:buildAndCollect")
    }
}

publishing {
    publications.register<MavenPublication>("reforged") {
        artifactId = "yacl-reforged"
        artifact(releaseJar)
        artifact(tasks.named("sourcesJar"))
        pom {
            name = property("modName").toString()
            description = property("modDescription").toString()
            url = "https://modrinth.com/project/Uwg487V8"
            scm {
                url = "https://github.com/SvyatON/YACL-Reforged"
                connection = "scm:git:https://github.com/SvyatON/YACL-Reforged.git"
                developerConnection = "scm:git:ssh://git@github.com/SvyatON/YACL-Reforged.git"
            }
            issueManagement { system = "GitHub"; url = "https://github.com/SvyatON/YACL-Reforged/issues" }
            licenses { license { name = "LGPL-3.0-or-later"; url = "https://www.gnu.org/licenses/lgpl-3.0.en.html" } }
            developers {
                developer { id = "isXander"; name = "isXander" }
                developer { id = "SvyatON"; name = "SvyatON" }
            }
        }
    }
}

tasks.named("sourcesJar") { dependsOn(forgeEntrypoint) }

tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
