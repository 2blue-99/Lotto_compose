import java.util.Properties

plugins {
    kotlin("kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.lucky_lotto.mvi_test"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.lucky_lotto.mvi_test"
        minSdk = 24
        targetSdk = 35
        versionCode = 6
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val nativeAppKey = properties.getProperty("AD_APPLICATION_ID") ?: ""
        manifestPlaceholders["AD_APPLICATION_ID"] = nativeAppKey

        buildConfigField("String", "DEVELOPER_EMAIL", properties.getProperty("DEVELOPER_EMAIL"))
    }

    //
    lint {
        // androidx.lifecycle.lint.NonNullableMutableLiveDataDetector 버전 차이 이슈로 우회 처리
        disable += "NullSafeMutableLiveData"
    }

    buildTypes {
        release {
            buildConfigField("String", "AD_BOTTOM_BANNER_ID", properties.getProperty("AD_BOTTOM_BANNER_ID"))
            buildConfigField("String", "AD_DIALOG_BANNER_ID", properties.getProperty("AD_DIALOG_BANNER_ID"))
            buildConfigField("String", "AD_FULL_PAGE_ID", properties.getProperty("AD_FULL_PAGE_ID"))

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            applicationIdSuffix = "dev"

            buildConfigField("String", "AD_BOTTOM_BANNER_ID", properties.getProperty("AD_TEST_BANNER_ID"))
            buildConfigField("String", "AD_DIALOG_BANNER_ID", properties.getProperty("AD_TEST_BANNER_ID"))
            buildConfigField("String", "AD_FULL_PAGE_ID", properties.getProperty("AD_TEST_FULL_PAGE_ID"))
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
        buildConfig = true
    }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.foundation)
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
    implementation(libs.androidx.material)

    // Ad
//    implementation("com.google.android.gms:play-services-ads:24.4.0")

    // Flow Row
    implementation(libs.accompanist.flowlayout) // 최신 버전 확인 필요

    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.view)

    // ML Kit Barcode Scanning
    implementation(libs.barcode.scanning)

    implementation(libs.accompanist.permissions)

    // 애드몹 광고
    implementation(libs.play.services.ads)
    implementation(libs.play.services.ads.api)

    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
    implementation("com.google.firebase:firebase-analytics")

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