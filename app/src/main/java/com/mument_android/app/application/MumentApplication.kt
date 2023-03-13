package com.mument_android.app.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.mument_android.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MumentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKakaoLogin()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        /*val fontRequest = FontRequest( TODO 안 쓰면 지우기
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs)*/
//
//        val config = FontRequestEmojiCompatConfig(this, fontRequest)
//        EmojiCompat.init(config)

    }

    private fun initKakaoLogin() {
        val kakaoAppKey = "dcf1de7e11089f484ac873f0e833427d"
        KakaoSdk.init(this, kakaoAppKey)
    }
}