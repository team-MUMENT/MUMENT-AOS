package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.MoveFromHistoryToDetail
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.FROM_HISTORY
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core.util.Constants.POP_BACKSTACK_KEY
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.domain.entity.music.MusicInfoEntity
import javax.inject.Inject

class MoveFromHistoryToDetailImpl @Inject constructor(private val activity: Activity) :
    MoveFromHistoryToDetail {
    override fun moveMumentDetail(mumentId: String, musicInfoEntity: MusicInfoEntity, popBackStack: Boolean) {
        with(activity as HistoryActivity) {
            Intent(this, MainActivity::class.java).apply {
                putExtra(POP_BACKSTACK_KEY, popBackStack)
                putExtra(MUMENT_ID, mumentId)
                putExtra(MUSIC_INFO_ENTITY, musicInfoEntity)
                putExtra(FROM_HISTORY, FROM_HISTORY)
                startActivity(this)
            }
        }
    }

    override fun moveMusicDetail(musicInfoEntity: MusicInfoEntity, popBackStack: Boolean) {
        with(activity as HistoryActivity) {
            Intent(this, MainActivity::class.java).apply {
                putExtra(POP_BACKSTACK_KEY, popBackStack)
                putExtra(MUSIC_INFO_ENTITY, musicInfoEntity)
                putExtra(FROM_HISTORY, FROM_HISTORY)
                startActivity(this)
            }
        }
    }

    override fun popBackToMain() {
        with(activity as HistoryActivity) {
            Intent(this, MainActivity::class.java).apply {
                putExtra(FROM_HISTORY, FROM_HISTORY)
                putExtra(POP_BACKSTACK_KEY, true)
                startActivity(this)
            }
        }
    }
}