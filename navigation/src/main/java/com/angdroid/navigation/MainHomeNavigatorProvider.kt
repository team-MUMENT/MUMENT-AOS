package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MainHomeNavigatorProvider {
    fun profileSettingToMain()
    fun searchToMain(selectedMusicId: MusicInfoEntity)
}