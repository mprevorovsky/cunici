pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}
rootProject.name = "cunici"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.4.0")
}