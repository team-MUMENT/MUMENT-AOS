package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import com.mument_android.R
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.presentation.ui.main.MainActivity
import javax.inject.Inject

class MoveRecordProviderImpl @Inject constructor(private val activity: Activity) :
    MoveRecordProvider {
    override fun recordMusic(music: Music) {
        with(activity as MainActivity) {
            recordMusic(music)
            binding.navBar.selectedItemId = R.id.fragment_record
        }
    }
}