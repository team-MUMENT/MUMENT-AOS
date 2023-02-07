package com.mument_android.domain.entity

data class LimitUserEntity(
    val endDate: String?,
    val musicArtist: String?,
    val musicTitle: String?,
    val period: String?,
    val reason: String?,
    val restricted: Boolean?
)
