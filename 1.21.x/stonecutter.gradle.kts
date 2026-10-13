plugins {
    id("dev.kikugie.stonecutter")
    kotlin("jvm") version "2.3.0" apply false
    id("net.minecraftforge.gradle") version "7.0.29" apply false
    id("net.minecraftforge.jarjar") version "0.2.3" apply false
}
stonecutter active file("versions/current")
tasks.register("buildReleases") {
    group = "build"
    dependsOn(subprojects.map { "${it.path}:buildAndCollect" })
}
