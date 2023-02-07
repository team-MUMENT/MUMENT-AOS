package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.content.Context
import android.content.Intent
import com.angdroid.navigation.LogInNavigatorProvider
import com.mument_android.login.LogInActivity
import javax.inject.Inject

class LogInNavigatorProviderImpl @Inject constructor(
    private val context: Context
): LogInNavigatorProvider {
    override fun navToLogin() {
        Intent(context, LogInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }
    }
}