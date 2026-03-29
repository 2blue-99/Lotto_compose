plugins {
    kotlin("kapt")
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.lucky_lotto.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        buildConfig = true
    }
}

dependencies {
    implementation(projects.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    /* hilt */
    implementation(libs.hilt)
    kapt (libs.dagger.hilt.compiler)
    kapt (libs.androidx.hilt.compiler)

    /* room */
    implementation(libs.room.runtime)
    kapt (libs.room.compiler)
    implementation (libs.room.ktx) // Room Flow

    /* data store */
    implementation(libs.androidx.dataStore.preferences)

    /* retrofit */
    implementation (libs.retrofit)
    implementation (libs.retrofit.converter.moshi)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)


    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    // Add the dependencies for the Remote Config and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-analytics")

    // Log
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}