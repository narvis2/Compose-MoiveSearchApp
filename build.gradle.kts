buildscript {
    dependencies {
        classpath (BuildPlugins.DAGGER_HILT)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "7.3.0" apply false
    id ("com.android.library") version "7.3.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.6.10" apply false
}