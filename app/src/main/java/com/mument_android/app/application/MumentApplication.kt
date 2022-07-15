package com.mument_android.app.application

import android.app.Application
import androidx.core.provider.FontRequest
import com.mument_android.BuildConfig
import com.mument_android.R
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MumentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs)
//
//        val config = FontRequestEmojiCompatConfig(this, fontRequest)
//        EmojiCompat.init(config)
    }
}