plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "org.comixedproject.variant.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "org.comixedproject.variant.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.adaptive.layout.android)

    val composeBom = platform(libs.androidx.compose.bom)

    implementation(composeBom)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.kotlinx.datetime)

    implementation(libs.coil.compose.android)
    implementation(libs.readium.shared)
    implementation(libs.readium.opds)
    implementation(libs.readium.streamer)
    implementation(libs.readium.navigator)

    testImplementation(libs.bundles.unit.tests)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.instrumented.tests)

    debugImplementation(libs.bundles.compose.debug)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
}

kover {

}