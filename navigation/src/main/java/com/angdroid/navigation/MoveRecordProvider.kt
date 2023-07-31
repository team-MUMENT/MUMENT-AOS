package com.angdroid.navigation

import com.mument_android.domain.entity.musicdetail.Music

interface MoveRecordProvider {
    fun recordMusic(music: Music)
}