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
}

val mcVersion = property("mcVersion").toString()
val releaseMinecraft = findProperty("artifact.minecraft") ?: mcVersion
val releaseVersion = "${property("modVersion")}+$releaseMinecraft"
version = releaseVersion
group = "dev.svyaton"
base.archivesName = "yacl-reforged"
java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
    withSourcesJar()
}
stonecutter {
    constants {
        put("fabric", false)
        put("neoforge", false)
        put("forge", true)
        put("forgelike", true)
        put("controlify", false)
        put("mod-menu", false)
    }
}
minecraft {
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
    implementation(minecraft.dependency("net.minecraftforge:forge:$mcVersion-${property("deps.forge")}"))
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
        "org.jetbrains.kotlin:kotlin-reflect:2.3.0"
    )
    bundled.forEach {
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
    inputs.properties(metadata)
    inputs.property("resourcePackName", metadata.getValue("mod_name"))
    from(rootProject.file("src/main/templates/META-INF/mods.toml")) { into("META-INF"); expand(metadata) }
    filesMatching("pack.mcmeta") { expand(mapOf("name" to metadata.getValue("mod_name"))) }
    exclude("yacl-fabric.mixins.json")
}
tasks.withType<JavaCompile>().configureEach {
    dependsOn("stonecutterGenerate")
    options.encoding = "UTF-8"
    options.compilerArgs.add("-proc:none")
}
tasks.withType<KotlinCompile>().configureEach {
    dependsOn("stonecutterGenerate")
    compilerOptions.jvmTarget = JvmTarget.JVM_25
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
if (artifactTarget == project.name) {
    tasks.register<Copy>("buildAndCollect") {
        dependsOn("jarJar", "sourcesJar")
        from(tasks.named<Jar>("jarJar").flatMap { it.archiveFile })
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
        artifact(tasks.named("jarJar"))
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

tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
