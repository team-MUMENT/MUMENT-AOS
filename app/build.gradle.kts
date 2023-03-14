import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    signingConfigs {
        create("release") {
            storeFile = file("key_store_file/release.jks")
            storePassword = "alsgh478"
            keyAlias = "releaseKey"
            keyPassword = "alsgh478"
        }
    }
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))

        signingConfigs {
            register("release") {
                storeFile = file("signKey")
                storePassword = gradleLocalProperties(rootDir).getProperty("keystore_password")
                keyAlias = gradleLocalProperties(rootDir).getProperty("key_alias")
                keyPassword = gradleLocalProperties(rootDir).getProperty("key_alias_password")
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isDebuggable = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            buildConfigField("String", "BASE_URL", properties["BASE_URL"] as String)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = DefaultConfig.JVM_TARGET
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    addAndroidXDependencies()
    addNavigationDependencies()
    addRoomDependencies()
    addDaggerHiltDependencies()
    implementation(AndroidXDependencies.lifecycleJava8)
    addLifecycleDependencies()
    addTestDependencies()
    addNetworkDependencies()
    implementation(ThirdPartyDependencies.fireBaseGA)
    implementation(ThirdPartyDependencies.kakao)
    implementation(ThirdPartyDependencies.fireBaseCloudMessaging)
    implementation(ThirdPartyDependencies.gson)
    implementation(ThirdPartyDependencies.timber)
    implementation(AndroidXDependencies.dataStore)
    implementation(project(Modules.CORE_MODULE))
    implementation(project(Modules.CORE_DEPENDENT_MODULE))
    implementation(project(Modules.DOMAIN_MODULE))
    implementation(project(Modules.NAVIGATION_MODULE))
    implementation(project(Modules.DATA_MODULE))
    implementation(project(Modules.FEATURE_LOCKER_MODULE))
    implementation(project(Modules.FEATURE_LOGIN_MODULE))
    implementation(project(Modules.FEATURE_HOME_MODULE))
    implementation(project(Modules.FEATURE_RECORD_MODULE))
    implementation(project(Modules.FEATURE_DETAIL_MODULE))
    implementation(project(Modules.FEATURE_MY_PAGE_MODULE))
}