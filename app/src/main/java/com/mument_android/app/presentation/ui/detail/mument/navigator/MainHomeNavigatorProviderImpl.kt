package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.MainHomeNavigatorProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import javax.inject.Inject

class MainHomeNavigatorProviderImpl @Inject constructor(private val activity: Activity) :
    MainHomeNavigatorProvider {

    override fun moveMain(userId: Int) {
        with(activity as MainActivity) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}