package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.MumentHistoryNavigatorProvider
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import javax.inject.Inject

class MumentHistoryNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
): MumentHistoryNavigatorProvider {
    override fun mumentDetailToHistory(music: Music, userId: Int) {
        Intent(activity, HistoryActivity::class.java).apply {
            putExtra("music", music)
            putExtra("userId", userId)
            activity.startActivity(this)
        }
    }
}