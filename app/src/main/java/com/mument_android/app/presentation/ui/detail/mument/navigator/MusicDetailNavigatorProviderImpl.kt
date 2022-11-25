package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.os.Bundle
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.home.HomeFragment
import javax.inject.Inject

class MusicDetailNavigatorProviderImpl @Inject constructor(private val activity: Activity) :
    MusicDetailNavigatorProvider {
    override fun moveMusicDetail(musicId: String) {
        with(activity as MainActivity) {
            binding.navBar.selectedItemId = R.id.fragment_home
            val bundle = Bundle().also { it.putString(HomeFragment.MUSIC_ID, musicId) }
            this.navController.navigate(R.id.action_homeFragment_to_musicDetailFragment, bundle)
        }
    }
}