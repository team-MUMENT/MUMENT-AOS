import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())
android {
    namespace = DefaultConfig.APPLICATION_ID
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildToolsVersion = "30.0.3"
        multiDexEnabled = true

        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))
        buildConfigField("String", "KAKAO_NATIVE_KEY", properties.getProperty("KAKAO_NATIVE_KEY"))

        signingConfigs {
            register("release") {
                storeFile = file("key_store_file/release.jks")
                storePassword = gradleLocalProperties(rootDir).getProperty("keystore_password")
                keyAlias = gradleLocalProperties(rootDir).getProperty("key_alias")
                keyPassword = gradleLocalProperties(rootDir).getProperty("key_alias_password")
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
            addManifestPlaceholders(mapOf("enableCrashReporting" to true))
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", properties["BASE_URL"] as String)
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false       // to disable mapping file uploads (default=true if minifying)
            }
            addManifestPlaceholders(mapOf("enableCrashReporting" to false))
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
    addNavigationDependencies()
    addRoomDependencies()
    addDaggerHiltDependencies()
    implementation(AndroidXDependencies.lifecycleJava8)
    implementation(AndroidXDependencies.multiIndex)
    addLifecycleDependencies()
    addTestDependencies()
    addNetworkDependencies()
    implementation(platform(ThirdPartyDependencies.fireBasePlatform))
    implementation(ThirdPartyDependencies.fireCrashlytics)
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