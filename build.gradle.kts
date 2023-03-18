plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.2").apply(false)
    id("com.android.library").version("7.4.2").apply(false)
    id("org.sonarqube").version("3.4.0.2513")
    kotlin("android").version("1.8.10").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

sonarqube {
    properties {
        property("sonar.projectKey", "comixed_prestige")
        property("sonar.organization", "comixed")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}