plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "org.comixedproject.variant.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "org.comixedproject.variant.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.activity.compose.compiler.get()
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.adaptive.layout.android)

    val composeBom = platform(libs.androidx.compose.bom)

    implementation(composeBom)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)

    testImplementation(libs.bundles.unit.tests)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.instrumented.tests)

    debugImplementation(libs.bundles.compose.debug)
}