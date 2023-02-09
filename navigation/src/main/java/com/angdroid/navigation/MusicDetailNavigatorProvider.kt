package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MusicDetailNavigatorProvider {
    fun fromHomeToMusicDetail(music: MusicInfoEntity)
    fun fromMumentDetailToMusicDetail(music: MusicInfoEntity)
}