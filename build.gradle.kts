plugins {
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.androidApplication).apply(false)
    id("app.cash.sqldelight").version("2.0.0").apply(false)
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