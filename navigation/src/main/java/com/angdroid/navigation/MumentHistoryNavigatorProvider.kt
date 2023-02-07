package com.angdroid.navigation

import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music

interface MumentHistoryNavigatorProvider {
    fun mumentDetailToHistory(music: Music, userId: Int)
}