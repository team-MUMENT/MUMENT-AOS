import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
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
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    addAndroidXDependencies()
    addTestDependencies()
    addNavigationDependencies()
    addDaggerHiltDependencies()
    addLifecycleDependencies()
    implementation(ThirdPartyDependencies.coil)
    implementation(KotlinDependencies.coroutines)
    implementation(AndroidXDependencies.coroutines)
    implementation(ThirdPartyDependencies.flexBox)

    implementation(project(Modules.DOMAIN_MODULE))
    implementation(project(Modules.CORE_MODULE))
    implementation(project(Modules.CORE_DEPENDENT_MODULE))
}