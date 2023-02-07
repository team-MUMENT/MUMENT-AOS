package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.angdroid.navigation.MoveMusicDetailNavigatorProvider
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.home.search.SearchActivity
import javax.inject.Inject

class MoveMusicDetailNavigatorProviderImpl @Inject constructor(private val activity: Activity) :
    MoveMusicDetailNavigatorProvider {
    override fun musicMument(musicId: String) {
//        with(activity as MainActivity) {
//            musicMument(musicId)
//            binding.navBar.selectedItemId = R.id.fragment_home
//        }
    }

    override fun intentMusicDetail(musicId: String) {
        with(activity as SearchActivity) {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("MUSIC_ID", musicId)
            }
            setResult(AppCompatActivity.RESULT_OK, intent)
            finish()
        }
    }
}