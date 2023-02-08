package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MoveFromHistoryToDetail {
    fun moveMumentDetail(mumentId: String, musicInfoEntity: MusicInfoEntity)
    fun moveMusicDetail(musicInfoEntity: MusicInfoEntity)
}