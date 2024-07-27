plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.sqldelight).apply(false)

    alias(libs.plugins.sonarqube)
    alias(libs.plugins.kotlinter)
}

sonar {
    properties {
        property("sonar.projectKey", "comixed_variant")
        property("sonar.organization", "comixed")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.gradle.skipCompile", true)
    }
}

kotlinter {
    failBuildWhenCannotAutoFormat = false
    ignoreFailures = false
    reporters = arrayOf("checkstyle", "plain")
}