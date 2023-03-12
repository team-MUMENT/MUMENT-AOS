package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MusicDetailNavigatorProvider {
    fun fromHomeToMusicDetail(music: MusicInfoEntity, startNav: String)
    fun fromMumentDetailToMusicDetail(music: MusicInfoEntity, startNav: String?)
    fun musicDetailPopBackStack(startNav: String)
}