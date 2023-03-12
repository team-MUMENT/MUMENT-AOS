package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MumentDetailNavigatorProvider {
    fun moveHomeToMumentDetail(mumentId:String, musicInfo: MusicInfoEntity, startNav: String)
    fun moveLockerToMumentDetail(mumentId:String, musicInfo: MusicInfoEntity)
    fun musicDetailToMumentDetail(mumentId:String, musicInfo: MusicInfoEntity, startNav: String?)
    fun mumentDetailPopBackStack(startNav: String)
}