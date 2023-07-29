package com.mument_android.app.presentation.ui.detail.mument.navigator

import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.musicdetail.Music

interface EditMumentNavigator {
    fun editMument(mumentId: String, mumentDetailEntity: MumentDetailEntity)
    fun musicMument(musicId: String)
    fun recordMusic(music: Music)
}