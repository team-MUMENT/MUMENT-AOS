import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}


val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.mument_android.login"
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String","KAKAO_NATIVE_KEY", properties["KAKAO_NATIVE_KEY"] as String)
        manifestPlaceholders["kakaokey"] = getApiKey("KAKAO_NATIVE_KEY")
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
            buildConfigField("String","KAKAO_NATIVE_KEY", properties["KAKAO_NATIVE_KEY"] as String)
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


fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    addAndroidXDependencies()
    addTestDependencies()
    addNavigationDependencies()
    addDaggerHiltDependencies()
    addLifecycleDependencies()
    implementation(ThirdPartyDependencies.fireBaseCloudMessaging)
    implementation(ThirdPartyDependencies.indicator)
    implementation(ThirdPartyDependencies.coil)
    implementation(ThirdPartyDependencies.lottie)
    implementation(ThirdPartyDependencies.fireBaseGA)
    implementation(platform(ThirdPartyDependencies.fireBasePlatform))
    implementation(AndroidXDependencies.coroutines)
    implementation(ThirdPartyDependencies.kakao)
    implementation(project(Modules.DOMAIN_MODULE))
    implementation(project(Modules.CORE_MODULE))
    implementation(project(Modules.CORE_DEPENDENT_MODULE))
    implementation(project(Modules.NAVIGATION_MODULE))
}