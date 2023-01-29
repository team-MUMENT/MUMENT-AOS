package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.os.Bundle
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.google.gson.Gson
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.detail.mument.fragment.MumentDetailFragment.Companion.MUSIC_INFO
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.home.main.HomeFragment
import javax.inject.Inject

class MumentDetailNavigatorProviderImpl @Inject constructor(private val activity : Activity):MumentDetailNavigatorProvider {
    override fun moveMumentDetail(mumentId: String,musicInfo: MusicInfoEntity) {
        with(activity as MainActivity){
            binding.navBar.selectedItemId = R.id.fragment_home
            val bundle = Bundle()
                .also { it.putString(HomeFragment.MUMENT_ID, mumentId) }
                .also { it.putString(MUSIC_INFO, Gson().toJson(musicInfo)) }
            this.navController.navigate(R.id.action_homeFragment_to_mumentDetailFragment, bundle)
        }
    }
}