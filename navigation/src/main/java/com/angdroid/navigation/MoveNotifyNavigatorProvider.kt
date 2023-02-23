package com.angdroid.navigation

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MoveNotifyNavigatorProvider {
    fun moveToNoticeDetail(noticeId: Int)
    fun moveToMumentDetail(mumentId: String, musicInfoEntity: MusicInfoEntity, startNav: String)
}