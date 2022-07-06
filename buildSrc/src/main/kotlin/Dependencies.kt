object KotlinDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinDateTimeVersion}"
    const val kotlinxSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerializationJsonVersion}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesVersion}"
    const val hiltCore = "com.google.dagger:hilt-core:${Versions.hiltVersion}"
}

object AndroidXDependencies {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val desugarLibrary = "com.android.tools:desugar_jdk_libs:1.1.5"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"
    const val paging3 = "androidx.paging:paging-runtime:${Versions.pagingVersion}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroidVersion}"
    const val lifeCycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    const val lifecycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"
    const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val security = "androidx.security:security-crypto:${Versions.securityVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val junit = "androidx.test.ext:junit-ktx:${Versions.junit}"
    const val splashScreen = "androidx.core:core-splashscreen:1.0.0-beta02"
    const val cardview = "androidx.cardview:cardview:${Versions.cardviewVersion}"
}

object TestDependencies {
    const val jUnit = "junit:junit:${Versions.junitVersion}"
    const val androidTest = "androidx.test.ext:junit:${Versions.androidTestVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val room = "androidx.room:room-testing:${Versions.roomVersion}"
}

object MaterialDesignDependencies {
    const val materialDesign =
        "com.google.android.material:material:${Versions.materialDesignVersion}"
}

object KaptDependencies {
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
}

object ThirdPartyDependencies {
    const val playCore = "com.google.android.play:core:${Versions.playCoreVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"
    const val viewPagerIndicator =
        "com.tbuonomo.andrui:viewpagerdotsindicator:${Versions.viewPagerIndicatorVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val okHttpBom = "com.squareup.okhttp3:okhttp-bom:${Versions.okHttpVersion}"
    const val okHttp = "com.squareup.okhttp3:okhttp"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor"
    const val kotlinxSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinxSerializationConverterVersion}"
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val flipper = "com.facebook.flipper:flipper:${Versions.flipperVersion}"
    const val soloader = "com.facebook.soloader:soloader:${Versions.soloaderVersion}"
    const val flipperNetwork =
        "com.facebook.flipper:flipper-network-plugin:${Versions.flipperVersion}"
    const val flipperLeakCanary =
        "com.facebook.flipper:flipper-leakcanary2-plugin:${Versions.flipperVersion}"
    const val leakCanary =
        "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryVersion}"
    const val ossLicense =
        "com.google.android.gms:play-services-oss-licenses:${Versions.ossVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverterVersion}"
    const val naverAuth = "com.navercorp.nid:oauth:${Versions.naverAuth}"
}

object FirebaseDependency {
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBomVersion}"
    const val analyticsKtx = "com.google.firebase:firebase-analytics-ktx"
    const val crashlyticsKtx = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaeMessaging =
        "com.google.firebase:firebase-messaging:${Versions.firebaseMessagingVersion}"
}

object ClassPathPlugins {
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
    const val oss = "com.google.android.gms:oss-licenses-plugin:${Versions.ossPluginVersion}"
    const val googleService = "com.google.gms:google-services:${Versions.googleServiceVersion}"
    const val navArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navVersion}"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.8.1"
    const val appDistribution = "com.google.firebase:firebase-appdistribution-gradle:3.0.1"
}
