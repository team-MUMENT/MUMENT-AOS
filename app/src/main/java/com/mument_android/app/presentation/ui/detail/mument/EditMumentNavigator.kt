package com.mument_android.app.presentation.ui.detail.mument

import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music

interface EditMumentNavigator {
    fun editMument(mumentId: String, mumentDetailEntity: MumentDetailEntity)
    fun musicMument(musicId: String)
    fun recordMusic(music: Music)
}