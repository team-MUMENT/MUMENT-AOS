package com.mument_android.home.models

import com.mument_android.home.util.NotifyType

data class Notify(
    val id: Int,
    val type: NotifyType,
    val userId: Int,
    val isDeleted: Boolean,
    val isRead: Boolean,
    val createdAt: String,
    val linkId: Int,
    val noticePoint: String?,
    val noticeTitle: String?,
    val likeMusicTitle: String?,
    val likeProfileId: String?,
)