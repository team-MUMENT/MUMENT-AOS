object KotlinDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val kotlinxSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerializationJsonVersion}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesVersion}"
}

object AndroidXDependencies {
    const val window = "androidx.window:window:${Versions.window}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroidVersion}"
    const val lifeCycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    const val lifecycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val liveData =  "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"
    const val security = "androidx.security:security-crypto:${Versions.securityVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val junit = "androidx.test.ext:junit-ktx:${Versions.junit}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardviewVersion}"
    const val emoji = "androidx.emoji:emoji:${Versions.emoji}"
    const val dataStore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
    const val protoDataStore = "androidx.datastore:datastore:${Versions.protoDataStore}"
    const val dataStorePreference = "androidx.datastore:datastore-preferences-core:${Versions.datastorePreferences}"
    const val protoBuf = "com.google.protobuf:protobuf-javalite:${Versions.protoBuf}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"

    const val preference ="androidx.preference:preference-ktx:${Versions.preferences}"

}

object HiltDependencies {
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
}

object TestDependencies {
    const val jUnit = "junit:junit:${Versions.junitVersion}"
    const val androidTest = "androidx.test.ext:junit:${Versions.androidTestVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockitoCoreVersion}"
    const val mockitoAndroidTest = "org.mockito:mockito-android:${Versions.mockitoAndroidVersion}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2"
    const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
}

object MaterialDesignDependencies {
    const val materialDesign =
        "com.google.android.material:material:${Versions.materialDesignVersion}"
}

object KaptDependencies {
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
    const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
}

object ThirdPartyDependencies {
    const val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val okHttpBom = "com.squareup.okhttp3:okhttp-bom:${Versions.okHttpVersion}"
    const val okHttp = "com.squareup.okhttp3:okhttp"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor"
    const val kotlinxSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinxSerializationConverterVersion}"
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    const val gsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.gsonConverterVersion}"
    const val flexBox = "com.google.android.flexbox:flexbox:${Versions.flexBoxVersion}"
    const val indicator = "com.tbuonomo:dotsindicator:${Versions.dotsIndicator}"

}


object Plugins {
    const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
    const val SAFE_ARGS_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navVersion}"
}