plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.KOTLIN_PARCELIZE)
    id(BuildPlugins.HILT_PLUGIN)
}

android {
    namespace = "com.example.domain"
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
    // Hilt DI
    implementation(Dependencies.hilt.hilt)
    kapt(Dependencies.hilt.compiler)
    // Paging3 Pagination
    implementation(Dependencies.androidX.paging.runtime)
    testImplementation(Dependencies.androidX.paging.common)
    // Coroutine Thread
    implementation(Dependencies.coroutine.core)
    implementation(Dependencies.coroutine.android)
    // Joda DateTime
    implementation(Dependencies.joda)
    testImplementation(Dependencies.androidX.test.junit)
    androidTestImplementation(Dependencies.androidX.test.ext_junit)
    androidTestImplementation(Dependencies.androidX.test.espresso)
}