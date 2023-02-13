package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.angdroid.navigation.MoveFromHistoryToDetail
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.domain.entity.music.MusicInfoEntity
import javax.inject.Inject

class MoveFromHistoryToDetailImpl @Inject constructor(private val activity: Activity) :
    MoveFromHistoryToDetail {
    override fun moveMumentDetail(mumentId: String, musicInfoEntity: MusicInfoEntity) {
        with(activity as HistoryActivity) {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(MUMENT_ID, mumentId)
                putExtra(MUSIC_INFO_ENTITY, musicInfoEntity)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun moveMusicDetail(musicInfoEntity: MusicInfoEntity) {
        with(activity as HistoryActivity) {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(MUSIC_INFO_ENTITY, musicInfoEntity)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}