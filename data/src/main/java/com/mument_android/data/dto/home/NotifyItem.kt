package com.mument_android.data.dto.home

data class NotifyItem(
    val id: Int,
    val type: String,
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