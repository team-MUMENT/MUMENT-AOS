package com.angdroid.navigation

import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music

interface HistoryNavigatorProvider {
    fun moveHistory(music:Music, userId:Int)
}