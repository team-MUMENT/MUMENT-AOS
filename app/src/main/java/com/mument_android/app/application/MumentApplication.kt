package com.mument_android.app.application

import androidx.multidex.MultiDexApplication
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kakao.sdk.common.KakaoSdk
import com.mument_android.BuildConfig
import com.mument_android.data.BuildConfig.KAKAO_NATIVE_KEY
import com.mument_android.domain.entity.musicdetail.Music
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.*

@HiltAndroidApp
class MumentApplication : MultiDexApplication() {
    var historyBackStack = Stack<Triple<String, Int, Music>>()

    override fun onCreate() {
        super.onCreate()
        initKakaoLogin()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    private fun initKakaoLogin() {
        val kakaoAppKey = KAKAO_NATIVE_KEY
        KakaoSdk.init(this, kakaoAppKey)

    }
}