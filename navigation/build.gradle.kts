plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}


android {
    namespace = "com.mument_android.navigation"
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
}


dependencies {
    addTestDependencies()
    implementation(project(Modules.DOMAIN_MODULE))
}