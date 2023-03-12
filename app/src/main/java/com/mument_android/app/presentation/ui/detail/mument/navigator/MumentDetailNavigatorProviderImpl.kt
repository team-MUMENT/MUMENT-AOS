package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.google.gson.Gson
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core.util.Constants.START_NAV_KEY
import com.mument_android.domain.entity.music.MusicInfoEntity
import javax.inject.Inject

class MumentDetailNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
) : MumentDetailNavigatorProvider {
    override fun moveHomeToMumentDetail(
        mumentId: String,
        musicInfo: MusicInfoEntity,
        startNav: String
    ) {
        moveToMumentDetail(
            R.id.action_homeFragment_to_mumentDetailFragment,
            mumentId,
            musicInfo,
            startNav
        )
    }

    override fun moveLockerToMumentDetail(mumentId: String, musicInfo: MusicInfoEntity) {
        moveToMumentDetail(
            R.id.action_lockerFragment_to_mumentDetailFragment,
            mumentId,
            musicInfo,
            null
        )
    }

    override fun musicDetailToMumentDetail(mumentId: String, musicInfo: MusicInfoEntity, startNav: String?) {
        moveToMumentDetail(
            R.id.action_musicDetailFragment_to_mumentDetailFragment,
            mumentId,
            musicInfo,
            startNav
        )
    }

    private fun moveToMumentDetail(
        actionId: Int,
        mumentId: String,
        musicInfo: MusicInfoEntity,
        startNav: String?
    ) {
        if (activity is MainActivity) {
            val bundle = Bundle().apply {
                putString(MUMENT_ID, mumentId)
                putParcelable(MUSIC_INFO_ENTITY, musicInfo)
                putString(START_NAV_KEY, startNav)
            }
            activity.navController.navigate(actionId, bundle)
        }
    }

    override fun mumentDetailPopBackStack(startNav: String) {
        if (activity is MainActivity) {
            activity.navController.previousBackStackEntry?.savedStateHandle?.set(
                START_NAV_KEY,
                startNav
            )
            activity.navController.popBackStack()
        }
    }
}