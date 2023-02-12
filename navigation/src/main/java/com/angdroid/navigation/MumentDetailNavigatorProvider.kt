package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MumentDetailNavigatorProvider {
    fun moveHomeToMumentDetail(mumentId:String, musicInfo: MusicInfoEntity)
    fun moveLockerToMumentDetail(mumentId:String, musicInfo: MusicInfoEntity)
    fun musicDetailToMumentDetail(mumentId:String, musicInfo: MusicInfoEntity)
}