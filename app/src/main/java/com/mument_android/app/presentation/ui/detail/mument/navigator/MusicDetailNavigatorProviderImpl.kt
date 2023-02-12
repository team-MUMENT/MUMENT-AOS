package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavOptions
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.record.RecordActivity
import javax.inject.Inject

/** 여러 Class에 불필요하게 중복 선언된 상수들 하나로 통일하기 ex) MUSIC_ID **/

class MusicDetailNavigatorProviderImpl @Inject constructor(private val activity: Activity) :
    MusicDetailNavigatorProvider {
    override fun fromHomeToMusicDetail(music: MusicInfoEntity) {
        with(activity as MainActivity) {
            val bundle = Bundle().also { it.putParcelable(MUSIC_INFO_ENTITY, music) }
            navController.navigate(
                R.id.musicDetailFragment,
                bundle,
                NavOptions.Builder().setPopUpTo(R.id.musicDetailFragment, false).build()
            )
        }
    }

    override fun fromMumentDetailToMusicDetail(music: MusicInfoEntity) {
        Log.e("fromMumentDetailToMusicDetail", music.toString())
        with(activity as MainActivity) {
            if (navController.isFragmentInBackStack(R.id.musicDetailFragment)) {
                navController.popBackStack(R.id.musicDetailFragment, false)
            } else {
                Log.e("NOT EXIST BACK STACK", music.toString())
                val bundle = Bundle().apply { putParcelable(MUSIC_INFO_ENTITY, music) }
                this.navController.navigate(
                    R.id.action_mumentDetailFragment_to_musicDetailFragment,
                    bundle
                )
            }
        }
    }
}