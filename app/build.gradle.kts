@file:Suppress("UNUSED_EXPRESSION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.hadirly"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hadirly"
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.applandeo:material-calendar-view:1.9.0")
    implementation ("com.google.android.material:material:1.9.0")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.google.zxing:core:3.4.1")
    implementation ("com.google.zxing:zxing-parent:3.5.3")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}