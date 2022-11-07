plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.moviesearchapp"
    compileSdk = ConfigData.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.example.moviesearchapp"
        minSdk = ConfigData.MIN_SDK_VERSION
        targetSdk = ConfigData.TARGET_SDK_VERSION
        versionCode = ConfigData.VERSION_CODE
        versionName = ConfigData.VERSION_NAME

        buildConfigField ("String", "NAVE_CLIENT_ID", "\"fqY6A3gq_6L8pmLCZZsC\"")
        buildConfigField ("String", "NAVE_CLIENT_SECRET", "\"RI2nRoBaPc\"")
        buildConfigField ("String", "API_HOST", "\"https://openapi.naver.com/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Dependencies.androidX.core)
    implementation(Dependencies.androidX.lifecycle.runtime)
    implementation(Dependencies.androidX.compose_activity)
    implementation(Dependencies.androidX.compose_ui.ui)
    implementation(Dependencies.androidX.compose_ui.preview)
    implementation(Dependencies.androidX.material.compose_material)

    // Hilt DI
    implementation(Dependencies.hilt.hilt)
    kapt(Dependencies.hilt.compiler)
    implementation(Dependencies.hilt.compose)

    // Compose ViewModel
    implementation(Dependencies.androidX.lifecycle.viewModel)

    // Joda DateTime
    implementation(Dependencies.joda)

    // Compose Material Icon
    implementation(Dependencies.androidX.material_icon.extended)
    implementation(Dependencies.androidX.material_icon.core)

    // Timer Log
    implementation(Dependencies.timber)

    // Coroutine Thread
    implementation(Dependencies.coroutine.core)
    implementation(Dependencies.coroutine.android)

    // Retrofit + OkHttp Network
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit.gsonConverter)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttp.loggingInterceptor)

    // Glide Image
    implementation(Dependencies.image.glide)
    kapt(Dependencies.image.compiler)

    implementation(Dependencies.image.coil)

    // Compose Navigation
    implementation(Dependencies.androidX.navigation.navigation)
    // Compose Navigation + BottomSheet
    implementation(Dependencies.androidX.navigation.bottomNavigation)
    // Compose SwipeRefreshLayout
    implementation(Dependencies.swipe_refresh)

    // Paging3 Pagination
    implementation(Dependencies.androidX.paging.runtime)
    implementation(Dependencies.androidX.paging.compose)
    testImplementation(Dependencies.androidX.paging.common)

    // Room
    implementation(Dependencies.androidX.room.runtime)
    implementation(Dependencies.androidX.room.ktx)
    kapt(Dependencies.androidX.room.compiler)

    implementation(Dependencies.ratingBar)

    testImplementation(Dependencies.androidX.test.junit)
    androidTestImplementation(Dependencies.androidX.test.ext_junit)
    androidTestImplementation(Dependencies.androidX.test.espresso)
    androidTestImplementation(Dependencies.androidX.compose_ui.junit4)
    debugImplementation(Dependencies.androidX.compose_ui.tooling)
    debugImplementation(Dependencies.androidX.compose_ui.manifest)

    implementation(project(":data"))
    implementation(project(":domain"))
}