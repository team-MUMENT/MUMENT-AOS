package com.angdroid.navigation

import com.mument_android.domain.entity.musicdetail.Music

interface HistoryNavigatorProvider {
    fun moveHistory(music: Music, userId: Int)
}