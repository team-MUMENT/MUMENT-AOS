import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.mument_android"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))
    }

    buildTypes {
        getByName("release") {
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
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(AndroidXDependencies.coreKtx)
    implementation(AndroidXDependencies.appCompat)
    implementation(MaterialDesignDependencies.materialDesign)
    implementation(AndroidXDependencies.constraintLayout)
    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidTest)
    androidTestImplementation(TestDependencies.espresso)

    //navigation component
    implementation(AndroidXDependencies.navigationFragment)
    implementation(AndroidXDependencies.navigationUI)

    //coil
    implementation(ThirdPartyDependencies.coil)

    //Lottie
    implementation(ThirdPartyDependencies.lottie)

    //cardView
    implementation(AndroidXDependencies.cardview)

    //coRoutine
    implementation(KotlinDependencies.coroutines)

    //retrofit
    implementation(ThirdPartyDependencies.gsonConverter)
    implementation(ThirdPartyDependencies.retrofit)

    //okhttp
    implementation(ThirdPartyDependencies.okHttp)
    implementation(ThirdPartyDependencies.okHttpBom)
    implementation(ThirdPartyDependencies.okHttpLoggingInterceptor)

    //gson
    implementation(ThirdPartyDependencies.gson)
    implementation(KotlinDependencies.kotlinxSerialization)

    //Timber
    implementation(ThirdPartyDependencies.timber)

    //recyclerview
    implementation(AndroidXDependencies.recyclerView)

    // Jetpack Lifecycle
    implementation(AndroidXDependencies.coroutines)
    implementation(AndroidXDependencies.lifeCycleKtx)
    implementation(AndroidXDependencies.lifecycleJava8)

    // Hilt
    implementation(HiltDependencies.hilt)
    kapt(KaptDependencies.hiltAndroidCompiler)

    // Emoji
    implementation(AndroidXDependencies.emoji)
    implementation(TestDependencies.mockito)
    androidTestImplementation(TestDependencies.mockitoAndroidTest)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")


}