package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.QuitMainNavigatorProvider
import com.mument_android.login.LogInActivity
import javax.inject.Inject
import kotlin.system.exitProcess


class QuitMainNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
): QuitMainNavigatorProvider {
    override fun quitMument(isQuit: Boolean) {
        activity.finishAffinity()
        System.runFinalization()
        val intent = Intent(activity, LogInActivity::class.java)
        intent.putExtra("isQuit", true)
        activity.startActivity(intent)
        exitProcess(0)
    }
}