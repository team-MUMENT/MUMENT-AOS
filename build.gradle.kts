
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}")
    }
}

plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
    /*id("com.google.protobuf") version "0.8.17" apply false*/
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}