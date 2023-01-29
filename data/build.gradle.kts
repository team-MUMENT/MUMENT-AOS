import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))
        buildConfigField("String", "USER_ID", properties.getProperty("USER_ID"))
    }

    buildTypes {
        release {
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
}

dependencies {
    addTestDependencies()
    addNetworkDependencies()
    addDaggerHiltDependencies()
    addRoomDependencies()
    addNetworkDependencies()
    addLifecycleDependencies()

    implementation(ThirdPartyDependencies.timber)
    implementation(project(Modules.DOMAIN_MODULE))
    implementation(project(Modules.CORE_MODULE))
    implementation(project(Modules.CORE_DEPENDENT_MODULE))
}