package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.MypageNavigatorProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.mypage.MyPageActivity
import javax.inject.Inject

class MypageNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
) : MypageNavigatorProvider {
    override fun navToMyPage() {
        val intent = Intent(activity, MyPageActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}