plugins {
    kotlin("kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.mvi_test"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mvi_test"
        minSdk = 24
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
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    /* hilt */
    implementation(libs.hilt)
    kapt (libs.dagger.hilt.compiler)
    kapt (libs.androidx.hilt.compiler)

    // Hilt + Compose hiltViewModel()
    implementation(libs.androidx.hilt.navigation.compose)

    // ViewModel + Compose (기본 Compose 상태 관리용)
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Log
    implementation(libs.timber)

    // Material
    implementation("androidx.compose.material:material:1.8.3")

    // Ad
//    implementation("com.google.android.gms:play-services-ads:24.4.0")

    // Flow Row
    implementation(libs.accompanist.flowlayout) // 최신 버전 확인 필요

    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")

    // ML Kit Barcode Scanning
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    implementation(libs.accompanist.permissions)

    implementation("com.google.android.gms:play-services-ads:24.4.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Truth - 테스트 로그 라이브러리
    implementation(libs.truth)
    implementation(libs.truth.java8.extension) // Truth가 Java 8에 도입된 기능을 이용해 테스트하도록 만들어주는 추가 라이브러리
}