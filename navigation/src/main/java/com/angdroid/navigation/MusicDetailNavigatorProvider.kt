package com.angdroid.navigation

interface MusicDetailNavigatorProvider {
    fun moveMusicDetail(musicId:String)
    fun fromMumentDetailToMusicDetail(musicId: String)
}