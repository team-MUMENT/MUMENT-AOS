package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.DeclareNavigatorProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.detail.mument.activity.DeclarMumentActivity
import javax.inject.Inject

class DeclareNavigatorProviderImpl @Inject constructor(private val activity: Activity) :
DeclareNavigatorProvider{
    override fun moveDeclare(mumentId: String) {
        with(activity as MainActivity) {
            startActivity(Intent(this, DeclarMumentActivity::class.java).apply {
                putExtra(MUMENT_ID, mumentId)
            })
        }

    }
}