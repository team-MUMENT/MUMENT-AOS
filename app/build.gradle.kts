import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", properties["BASE_URL"] as String)
        buildConfigField("String", "USER_ID", properties["USER_ID"] as String)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            buildConfigField("String", "BASE_URL", properties["BASE_URL"] as String)
            buildConfigField("String", "USER_ID", properties["USER_ID"] as String)
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