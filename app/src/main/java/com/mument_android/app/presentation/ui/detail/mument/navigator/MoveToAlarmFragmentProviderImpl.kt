package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.MoveToAlarmFragmentProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.mypage.MyPageActivity
import javax.inject.Inject

class MoveToAlarmFragmentProviderImpl @Inject constructor(private val activity: Activity) :
    MoveToAlarmFragmentProvider {

    override fun moveAlarmFromMusic() {
        with(activity as MainActivity) {
            Intent(this, MyPageActivity::class.java).apply {
                putExtra("alarm", true)
                startActivity(this)
            }
        }
    }
}