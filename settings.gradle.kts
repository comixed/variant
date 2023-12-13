rootProject.name = "variant"
include(":shared")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("multiplatform").version("1.8.10")
        id("com.android.library").version("7.4.2")
        id("app.cash.sqldelight").version("2.0.0")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
