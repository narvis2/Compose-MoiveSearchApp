plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.data"
    compileSdk = ConfigData.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = ConfigData.MIN_SDK_VERSION
        targetSdk = ConfigData.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(Dependencies.androidX.core)
    implementation(Dependencies.androidX.appcompat)
    implementation(Dependencies.androidX.material.material)

    // Hilt
    implementation(Dependencies.hilt.hilt)
    kapt(Dependencies.hilt.compiler)
    // Coroutine
    implementation(Dependencies.coroutine.core)
    implementation(Dependencies.coroutine.android)
    // Log
    implementation(Dependencies.timber)
    // Retrofit + OkHttp
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit.gsonConverter)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttp.loggingInterceptor)
    // Paging3
    implementation(Dependencies.androidX.paging.runtime)
    testImplementation(Dependencies.androidX.paging.common)

    // Room
    implementation(Dependencies.androidX.room.runtime)
    implementation(Dependencies.androidX.room.ktx)
    kapt(Dependencies.androidX.room.compiler)

    // Joda
    implementation(Dependencies.joda)

    testImplementation(Dependencies.androidX.test.junit)
    androidTestImplementation(Dependencies.androidX.test.ext_junit)
    androidTestImplementation(Dependencies.androidX.test.espresso)
    implementation(project(":domain"))
}