package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.HistoryNavigatorProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUSIC
import com.mument_android.core.util.Constants.USER_ID
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.domain.entity.musicdetail.Music
import javax.inject.Inject

class HistoryNavigatorProviderImpl @Inject constructor(private val activity: Activity) :
    HistoryNavigatorProvider {
    override fun moveHistory(music: Music, userId: Int) {
        with(activity as MainActivity) {
            startActivity(Intent(this, HistoryActivity::class.java).apply {
                putExtra(MUSIC, music)
                putExtra(USER_ID, userId)
            })
        }
    }
}