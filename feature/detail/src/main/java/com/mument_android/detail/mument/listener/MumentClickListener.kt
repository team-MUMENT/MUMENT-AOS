package com.mument_android.detail.mument.listener

import com.mument_android.domain.entity.music.MusicInfoEntity

interface MumentClickListener {
    fun showMumentDetail(mumentId: String)
    fun likeMument(mumentId: String)
    fun cancelLikeMument(mumentId: String)
}