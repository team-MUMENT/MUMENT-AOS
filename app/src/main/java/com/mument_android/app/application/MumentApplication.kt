package com.mument_android.app.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.mument_android.BuildConfig
import com.mument_android.data.BuildConfig.KAKAO_NATIVE_KEY
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.*

@HiltAndroidApp
class MumentApplication: Application() {
    val historyBackStack = Stack<Triple<String, Int, Music>>()

    override fun onCreate() {
        super.onCreate()
        initKakaoLogin()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initKakaoLogin() {
        val kakaoAppKey = KAKAO_NATIVE_KEY
        KakaoSdk.init(this, kakaoAppKey)

    }
}