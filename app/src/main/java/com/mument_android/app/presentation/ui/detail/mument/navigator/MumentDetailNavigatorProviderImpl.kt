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

class MumentDetailNavigatorProviderImpl @Inject constructor (
    private val activity: Activity
): MumentDetailNavigatorProvider {
    override fun moveHomeToMumentDetail(mumentId: String, musicInfo: MusicInfoEntity) {
        moveToMumentDetail(R.id.action_homeFragment_to_mumentDetailFragment, mumentId, musicInfo)
    }

    override fun moveLockerToMumentDetail(mumentId: String, musicInfo: MusicInfoEntity) {
        moveToMumentDetail(R.id.action_lockerFragment_to_mumentDetailFragment, mumentId, musicInfo)
    }

    override fun musicDetailToMumentDetail(mumentId: String, musicInfo: MusicInfoEntity) {
        moveToMumentDetail(R.id.action_musicDetailFragment_to_mumentDetailFragment, mumentId, musicInfo)
    }

    override fun mainActivityToMumentDetail(mumentId: String, musicInfo: MusicInfoEntity) {
    }

    private fun moveToMumentDetail(actionId: Int, mumentId: String, musicInfo: MusicInfoEntity) {
        if(activity is MainActivity) {
            val bundle = Bundle()
                .also { it.putString(HomeFragment.MUMENT_ID, mumentId) }
                .also { it.putString(MUSIC_INFO, Gson().toJson(musicInfo)) }
            activity.navController.navigate(actionId, bundle)
        }
    }
}