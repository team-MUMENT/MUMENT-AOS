package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MusicDetailNavigatorProvider {
    fun moveMusicDetail(musicId:String)
    fun fromMumentDetailToMusicDetail(music: MusicInfoEntity)
}