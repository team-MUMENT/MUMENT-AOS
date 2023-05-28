plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK

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
    addTestDependencies()
    addNavigationDependencies()
    addDaggerHiltDependencies()
    addLifecycleDependencies()
    implementation(ThirdPartyDependencies.lottie)
    implementation(ThirdPartyDependencies.coil)
    implementation(AndroidXDependencies.coroutines)
    implementation(ThirdPartyDependencies.flexBox)
    implementation(ThirdPartyDependencies.fireBaseGA)
    implementation(platform(ThirdPartyDependencies.fireBasePlatform))
    implementation(project(Modules.DOMAIN_MODULE))
    implementation(project(Modules.CORE_MODULE))
    implementation(project(Modules.CORE_DEPENDENT_MODULE))
    implementation(project(Modules.NAVIGATION_MODULE))
    implementation(project(Modules.FEATURE_DETAIL_MODULE))
    implementation(project(Modules.FEATURE_MY_PAGE_MODULE))
}