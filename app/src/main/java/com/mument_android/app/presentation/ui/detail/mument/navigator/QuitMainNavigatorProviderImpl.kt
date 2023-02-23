package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.angdroid.navigation.QuitMainNavigatorProvider
import com.mument_android.login.LogInActivity
import javax.inject.Inject
import kotlin.system.exitProcess


class QuitMainNavigatorProviderImpl @Inject constructor(
    private val activity: Activity,
    private val application: Application
): QuitMainNavigatorProvider {
    override fun quitMument() {
        val intent = Intent(activity, LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.finishAffinity()
        activity.startActivity(intent)
        exitProcess(0)
    }
}