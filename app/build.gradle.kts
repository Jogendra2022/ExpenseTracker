plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.myexpense.expensetracker"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.myexpense.expensetracker"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    // ---------------------------------------------------------
    // CORE ANDROID
    // ---------------------------------------------------------

    implementation(
        libs.androidx.core.ktx
    )

    implementation(
        libs.androidx.lifecycle.runtime.ktx
    )

    implementation(
        libs.androidx.activity.compose
    )


    // ---------------------------------------------------------
    // JETPACK COMPOSE
    // ---------------------------------------------------------

    implementation(
        platform(
            libs.androidx.compose.bom
        )
    )

    implementation(
        libs.androidx.compose.ui
    )

    implementation(
        libs.androidx.compose.ui.graphics
    )

    implementation(
        libs.androidx.compose.ui.tooling.preview
    )

    implementation(
        libs.androidx.compose.material3
    )


    // ---------------------------------------------------------
    // MATERIAL ICONS
    // ---------------------------------------------------------

    implementation(
        "androidx.compose.material:material-icons-extended"
    )


    // ---------------------------------------------------------
    // NAVIGATION
    // ---------------------------------------------------------

    implementation(
        "androidx.navigation:navigation-compose:2.9.4"
    )


    // ---------------------------------------------------------
    // ROOM DATABASE
    // ---------------------------------------------------------

    implementation(
        "androidx.room:room-runtime:2.7.2"
    )

    implementation(
        "androidx.room:room-ktx:2.7.2"
    )

    ksp(
        "androidx.room:room-compiler:2.7.2"
    )


    // ---------------------------------------------------------
    // LIFECYCLE / VIEWMODEL
    // ---------------------------------------------------------

    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2"
    )

    implementation(
        "androidx.lifecycle:lifecycle-runtime-compose:2.9.2"
    )


    // ---------------------------------------------------------
    // FIREBASE
    // ---------------------------------------------------------

    implementation(
        platform(
            "com.google.firebase:firebase-bom:34.19.0"
        )
    )

    implementation(
        "com.google.firebase:firebase-auth"
    )


    // ---------------------------------------------------------
    // HILT DEPENDENCY INJECTION
    // ---------------------------------------------------------

    implementation(
        "com.google.dagger:hilt-android:2.57.1"
    )

    ksp(
        "com.google.dagger:hilt-compiler:2.57.1"
    )

    implementation(
        "androidx.hilt:hilt-navigation-compose:1.2.0"
    )


    // ---------------------------------------------------------
    // CONCURRENT FUTURES
    // ---------------------------------------------------------

    implementation(
        "androidx.concurrent:concurrent-futures:1.2.0"
    )


    // ---------------------------------------------------------
    // SPLASH SCREEN
    // ---------------------------------------------------------

    implementation(
        "androidx.core:core-splashscreen:1.0.1"
    )


    // ---------------------------------------------------------
    // TESTING
    // ---------------------------------------------------------

    testImplementation(
        libs.junit
    )

    androidTestImplementation(
        libs.androidx.junit
    )

    androidTestImplementation(
        libs.androidx.espresso.core
    )

    androidTestImplementation(
        platform(
            libs.androidx.compose.bom
        )
    )

    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    debugImplementation(
        libs.androidx.compose.ui.tooling
    )

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )
}