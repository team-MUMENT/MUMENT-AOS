package com.mument_android.domain.entity.mypage

import java.util.Date


data class UnregisterReasonEntity(
    val id: Int,
    val userId: Int,
    val profileId: String,
    val leaveCategoryId: Int,
    val leaveCategoryName: String,
    val reasonEtc: String?,
    val createdAt: Date
)
