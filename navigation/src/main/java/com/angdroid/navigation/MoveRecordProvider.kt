package com.angdroid.navigation

import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music

interface MoveRecordProvider {
    fun recordMusic(music: Music)
}