package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MoveMusicDetailNavigatorProvider {
    fun musicMument(musicId: String)
    fun intentMusicDetail(music: MusicInfoEntity)
}