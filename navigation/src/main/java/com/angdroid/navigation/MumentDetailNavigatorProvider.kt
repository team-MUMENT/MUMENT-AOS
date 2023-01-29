package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MumentDetailNavigatorProvider {
    fun moveMumentDetail(mumentId:String, musicInfo: MusicInfoEntity)
}