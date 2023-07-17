plugins {
    id("com.android.library")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mument_android.core_dependent"
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK
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
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = DefaultConfig.JVM_TARGET
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    addAndroidXDependencies()
    addTestDependencies()
    addDaggerHiltDependencies()
    addLifecycleDependencies()
    addNetworkDependencies()
    implementation(platform(ThirdPartyDependencies.fireBasePlatform))
    implementation(ThirdPartyDependencies.fireBaseGA)
    implementation(AndroidXDependencies.dataStore)
    implementation(project(Modules.CORE_MODULE))
}