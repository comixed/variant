plugins {
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    id("org.sonarqube") version "4.4.1.3373"
}

sonar {
    properties {
        property("sonar.projectKey", "comixed_variant")
        property("sonar.organization", "comixed")
        property("sonar.host.url", "https://sonarcloud.io")
        property("onar.gradle.skipCompile", true)
    }
}