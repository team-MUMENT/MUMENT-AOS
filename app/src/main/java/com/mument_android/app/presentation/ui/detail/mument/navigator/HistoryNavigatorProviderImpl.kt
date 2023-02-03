package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.angdroid.navigation.HistoryNavigatorProvider
import com.mument_android.R
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.home.main.HomeFragment
import javax.inject.Inject

class HistoryNavigatorProviderImpl @Inject constructor(private val activity: Activity) :
    HistoryNavigatorProvider {
    override fun moveHistory(music: Music, userId:Int) {
        with(activity as MainActivity) {
            when (binding.navBar.selectedItemId) {
                R.id.fragment_home -> {}
                else -> {
                    binding.navBar.selectedItemId = R.id.fragment_home
                }
            }
            startActivity(Intent(this, HistoryActivity::class.java).apply {
                putExtra("music", music)
                putExtra("userId", userId)
            })
        }
    }
}