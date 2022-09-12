package com.mument_android.app.presentation.ui.detail.mument.navigator

import com.startup.domain.entity.musicdetail.musicdetaildata.Music

interface MoveRecordProvider {
    fun recordMusic(music: Music)
}