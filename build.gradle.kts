import groovy.json.JsonSlurper
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import java.security.MessageDigest
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
val localQa = rootProject.file(".tools/qa/modern")
val testmod = sourceSets.create("testmod") {
    if (localQa.exists()) {
        java.srcDir(localQa.resolve("java"))
        resources.srcDir(localQa.resolve("resources"))
    }
    compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().output + sourceSets.main.get().runtimeClasspath
}
val qaModJar = tasks.register<Jar>("qaModJar") {
    archiveClassifier = "testmod"
    from(testmod.output)
    manifest.attributes("MixinConfigs" to if (localQa.exists()) "yacl-test.mixins.json,yacl-server-test.mixins.json" else "yacl-test.mixins.json")
}
minecraft {
    accessTransformers.from(rootProject.file("src/main/resources/META-INF/accesstransformer.cfg"))
    runs {
        configureEach {
            workingDir = layout.projectDirectory.dir("run")
            systemProperty("eventbus.api.strictRuntimeChecks", "true")
            if (providers.gradleProperty("qaKff").orNull == "true") systemProperty("yacl.reforged.qa.kff", "true")
            systemProperty("yacl.reforged.qa.kffId", providers.gradleProperty("qaKffId").orElse("kotlinforforge").get())
            systemProperty("yacl.reforged.qa.kotlinVersion", providers.gradleProperty("qaKotlinVersion").orElse("").get())
            systemProperty("yacl.reforged.qa.extraModId", providers.gradleProperty("qaExtraModId").orElse("").get())
            systemProperty("yacl.reforged.qa.noKotlin", providers.gradleProperty("qaNoKotlin").orElse("false").get())
            if (providers.gradleProperty("qaKffProbe").orNull == "true") systemProperty("yacl.reforged.qa.kffProbe", "true")
        }
        register("client") {
            if (providers.gradleProperty("qa").orNull == "true") {
                args("--mixin.config=yacl-test.mixins.json")
                systemProperty("yacl.reforged.qa", "true")
            }
            with(testmod) {
                args("--mixin.config=yacl.mixins.json", "--mixin.config=yacl-test.mixins.json")
            }
        }
        register("server") {
            args("--nogui")
            if (providers.gradleProperty("qaServer").orNull == "true") {
                args("--mixin.config=yacl-server-test.mixins.json")
                systemProperty("yacl.reforged.qa.server", "true")
            }
        }
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
val bundledRuntime = configurations.create("bundledRuntime") {
    isCanBeConsumed = false
    isCanBeResolved = true
    extendsFrom(configurations.getByName("jarJar"))
}
dependencies {
    implementation(minecraft.dependency("net.minecraftforge:forge:$mcVersion-${if (providers.gradleProperty("qaForgeTarget").orNull == project.name) providers.gradleProperty("qaForgeVersion").get() else property("deps.forge")}"))
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
val qaKffId = providers.gradleProperty("qaKffId").orElse("kotlinforforge").get()
val qaKffDependency = if (providers.gradleProperty("qaKff").orNull == "true") """
[[dependencies.yacl_test]]
modId = "$qaKffId"
mandatory = true
type = "required"
versionRange = "${if (qaKffId in setOf("kotlinreforged", "kotlin_reforged")) "[7,8)" else "[0,)"}"
ordering = "AFTER"
side = "BOTH"
""".trimIndent() else ""

tasks.named<ProcessResources>(testmod.processResourcesTaskName) {
    inputs.property("qaKffDependency", qaKffDependency)
    exclude("fabric.mod.json", "META-INF/neoforge.mods.toml")
    from(rootProject.file("src/testmod/templates/META-INF/mods.toml")) { into("META-INF"); expand(mapOf("qaKffDependency" to qaKffDependency)) }
    if (localQa.exists()) doLast {
        val resource = destinationDir.resolve("yacl-test.mixins.json")
        val config = JsonSlurper().parse(resource) as MutableMap<String, Any>
        val clients = config.getValue("client") as MutableList<String>
        if ("ReforgedQAMixin" !in clients) clients.add("ReforgedQAMixin")
        resource.writeText(groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(config)))
    }
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

if (providers.gradleProperty("qa").orNull == "true" || providers.gradleProperty("qaServer").orNull == "true" || providers.gradleProperty("packaged").orNull == "true") {
    afterEvaluate {
        for (runName in listOf("runClient", "runServer")) {
            val serverQA = runName == "runServer"
            val automaticQA = providers.gradleProperty(if (serverQA) "qaServer" else "qa").orNull == "true"
            if (!automaticQA && (serverQA || providers.gradleProperty("packaged").orNull != "true")) continue
            val resultName = if (serverQA) "qa-server-result.txt" else "qa-result.txt"
            val checksumName = if (serverQA) "qa-server-artifact.sha256" else "qa-artifact.sha256"
            tasks.named<JavaExec>(runName) {
                dependsOn("jarJar", qaModJar)
                val artifactTarget = findProperty("artifact.target")?.toString() ?: project.name
                if (artifactTarget != project.name && !providers.gradleProperty("qaJar").isPresent) dependsOn("buildAndCollect")
                if (automaticQA) doFirst {
                    val runDirectory = layout.projectDirectory.dir("run").asFile
                    runDirectory.resolve(resultName).delete()
                    runDirectory.resolve(checksumName).delete()
                    val configFile = runDirectory.resolve("config/yacl.json5")
                    configFile.parentFile.mkdirs()
                    val previous = if (configFile.exists()) configFile.readText() else "{showColorPickerIndicator: true}"
                    val key = Regex("preloadComplexImageFormats\\s*:\\s*(true|false)")
                    configFile.writeText(if (key.containsMatchIn(previous)) key.replace(previous, "preloadComplexImageFormats: true") else previous.replaceFirst("{", "{preloadComplexImageFormats: true,"))
                    val options = runDirectory.resolve("options.txt")
                    val settings = if (options.exists()) options.readText() else ""
                    options.writeText(settings.replace(Regex("(?m)^onboardAccessibility:.*$"), "onboardAccessibility:false").let { if (it.contains("onboardAccessibility:")) it else it + "\nonboardAccessibility:false\n" })
                }
                val ownJar = if (artifactTarget == project.name) tasks.named<Jar>("jarJar").flatMap { it.archiveFile } else rootProject.layout.buildDirectory.file("releases/yacl-reforged-$releaseVersion.jar")
                val overrideJar = providers.gradleProperty("qaJar")
                val actualJar = if (overrideJar.isPresent) layout.file(overrideJar.map { rootProject.file(it) }) else ownJar
                if (automaticQA) doLast {
                    val runDirectory = layout.projectDirectory.dir("run").asFile
                    val result = runDirectory.resolve(resultName)
                    check(result.exists() && result.readLines().firstOrNull() == "PASS") { "Packaged QA failed: ${if (result.exists()) result.readText() else "missing result"}" }
                    val digest = MessageDigest.getInstance("SHA-256").digest(actualJar.get().asFile.readBytes()).joinToString("") { "%02x".format(it) }
                    runDirectory.resolve(checksumName).writeText(digest)
                }
                val embeddedLibraries = bundledRuntime.resolvedConfiguration.firstLevelModuleDependencies.flatMap { it.moduleArtifacts }.map { it.file }.toSet()
                classpath = files(classpath.filter { it !in sourceSets.main.get().output.files && it !in testmod.output.files && it !in embeddedLibraries && !it.name.startsWith("kotlin-stdlib-") && !it.name.startsWith("kotlin-reflect-") }, qaModJar.flatMap { it.archiveFile }, actualJar)
                val actualRuntime = javaClass.getMethod("getRuntimeClasspath").apply { isAccessible = true }.invoke(this) as ConfigurableFileCollection
                actualRuntime.setFrom(testmod.runtimeClasspath.filter { it !in sourceSets.main.get().output.files && it !in testmod.output.files && it !in embeddedLibraries && !it.name.startsWith("kotlin-stdlib-") && !it.name.startsWith("kotlin-reflect-") }, qaModJar.flatMap { it.archiveFile }, actualJar)
            }
        }
    }
}

tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
