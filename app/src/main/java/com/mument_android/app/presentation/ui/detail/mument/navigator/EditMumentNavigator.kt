package com.mument_android.app.presentation.ui.detail.mument.navigator

import com.startup.domain.entity.detail.MumentDetailEntity
import com.startup.domain.entity.musicdetail.musicdetaildata.Music

interface EditMumentNavigator {
    fun editMument(mumentId: String, mumentDetailEntity: MumentDetailEntity)
    fun musicMument(musicId: String)
    fun recordMusic(music: Music)
}