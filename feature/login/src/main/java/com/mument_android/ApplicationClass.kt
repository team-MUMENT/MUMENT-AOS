package com.mument_android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.kakao.sdk.common.KakaoSdk

class ApplicationClass : Application(),Application.ActivityLifecycleCallbacks  {

    override fun onCreate() {
        super.onCreate()
        initKakaoLogin()
    }

    private fun initKakaoLogin() {
        val kakaoAppKey = "91f18886d3b19ea17119a59af77780ea"
        KakaoSdk.init(this, kakaoAppKey)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onActivityStarted(p0: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityResumed(p0: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityPaused(p0: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityStopped(p0: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(p0: Activity) {
        TODO("Not yet implemented")
    }


    //KakaoSdk.init(this, "{NATIVE_APP_KEY}")
}