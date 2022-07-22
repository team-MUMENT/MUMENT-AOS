package com.mument_android.app.presentation.ui.detail.mument

import android.app.Activity
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import javax.inject.Inject

class MoveMusicDetailNavigatorProviderImpl @Inject constructor(private val activity : Activity):MoveMusicDetailNavigatorProvider {
    override fun musicMument(musicId: String) {
        with(activity as MainActivity){
            musicMument(musicId)
            binding.navBar.selectedItemId = R.id.fragment_home_frame
        }
    }
}