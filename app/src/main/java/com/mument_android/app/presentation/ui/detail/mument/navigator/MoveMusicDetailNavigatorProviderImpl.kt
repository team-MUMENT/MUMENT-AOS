package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.angdroid.navigation.MoveMusicDetailNavigatorProvider
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.home.search.SearchActivity
import javax.inject.Inject

class MoveMusicDetailNavigatorProviderImpl @Inject constructor(private val activity: Activity) :
    MoveMusicDetailNavigatorProvider {
    override fun musicMument(musicId: String) {

    }

    override fun intentMusicDetail(music: MusicInfoEntity) {
//        with(activity as SearchActivity) {
//            activity.findNavController()
//            val intent = Intent(this, MainActivity::class.java).apply {
//                putExtra("MUSIC_INFO_ENTITY", music)
//            }
//            setResult(AppCompatActivity.RESULT_OK, intent)
//            finish()
//        }
    }
}