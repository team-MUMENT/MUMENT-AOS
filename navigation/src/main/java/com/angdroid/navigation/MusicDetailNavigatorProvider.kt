package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MusicDetailNavigatorProvider {
    fun fromHomeToMusicDetail(musicId:String)
    fun fromMumentDetailToMusicDetail(music: MusicInfoEntity)
}