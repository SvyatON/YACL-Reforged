import java.util.Properties

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.kikugie.dev/releases")
    }
}
plugins {
    id("dev.kikugie.stonecutter") version "0.9.8"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"
    val requested = providers.gradleProperty("targets").orNull?.split(',')?.map { it.removeSuffix("-forge") }
    create(rootProject) {
        val profiles = file("versions").listFiles().orEmpty().filter { it.isDirectory && it.resolve("gradle.properties").isFile }.associateWith { directory ->
            Properties().apply { directory.resolve("gradle.properties").inputStream().use { load(it) } }
        }
        val active = file("versions/current").readText().trim()
        val artifactTargets = profiles.filter { requested != null && it.value.getProperty("mcVersion") in requested }.values.mapNotNull { it.getProperty("artifact.target") }.toSet()
        for ((directory, properties) in profiles.toSortedMap(compareBy { it.name })) {
            val mc = properties.getProperty("mcVersion")
            val name = directory.name
            if (requested == null || mc in requested || name == active || name in artifactTargets) version(name, mc)
        }
    }
}
rootProject.name = "YACL Reforged"
