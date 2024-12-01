plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.pagando.owncollector"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pagando.owncollector"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // OkHttp y Logging Interceptor
    implementation("com.squareup.okhttp3:okhttp:4.9.3") // Última versión estable compatible con SDK 30
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

// Navigation Fragment y UI KTX
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2") // Compatible con SDK 30
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")

// ZXing Core (no requiere ajustes; es independiente del SDK)
    implementation("com.google.zxing:core:3.5.2")

// AppCompat
    implementation("androidx.appcompat:appcompat:1.3.1") // Compatible con SDK 30

// Compose Material para PullRefresh
    implementation("androidx.compose.material:material:1.2.1") // Compatible con SDK 30

// Coil para manejo de imágenes
    implementation("io.coil-kt:coil-compose:1.4.0") // Compatible con SDK 30
    implementation ("com.google.code.gson:gson:2.11.0")

}