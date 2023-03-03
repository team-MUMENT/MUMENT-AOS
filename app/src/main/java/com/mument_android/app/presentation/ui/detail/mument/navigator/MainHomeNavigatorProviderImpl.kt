package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.MainHomeNavigatorProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import javax.inject.Inject

class MainHomeNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
): MainHomeNavigatorProvider {
    override fun profileSettingToMain() {
        val intent = Intent(activity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.startActivity(intent)
    }
}