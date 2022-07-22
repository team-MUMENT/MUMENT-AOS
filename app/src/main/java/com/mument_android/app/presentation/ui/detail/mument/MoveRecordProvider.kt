package com.mument_android.app.presentation.ui.detail.mument

import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music

interface MoveRecordProvider {

    fun recordMusic(music: Music)
}