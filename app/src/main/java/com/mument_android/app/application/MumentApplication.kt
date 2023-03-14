package com.mument_android.app.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.mument_android.BuildConfig
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.*

@HiltAndroidApp
class MumentApplication: Application() {
    val historyBackStack = Stack<Pair<Int, Music>>()

    override fun onCreate() {
        super.onCreate()
        initKakaoLogin()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initKakaoLogin() {
        val kakaoAppKey = "dcf1de7e11089f484ac873f0e833427d"
        KakaoSdk.init(this, kakaoAppKey)

    }
}