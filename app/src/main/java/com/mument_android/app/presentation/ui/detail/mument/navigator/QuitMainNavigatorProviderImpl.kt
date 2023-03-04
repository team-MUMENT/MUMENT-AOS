package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.QuitMainNavigatorProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.login.LogInActivity
import com.mument_android.mypage.MyPageActivity
import javax.inject.Inject


class QuitMainNavigatorProviderImpl @Inject constructor(
    private val activity: Activity,
) : QuitMainNavigatorProvider {
    override fun quitMument() {
        with(activity as MyPageActivity) {
            val intentSecond = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("FINISH", true)
            }
            startActivity(intentSecond)
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}