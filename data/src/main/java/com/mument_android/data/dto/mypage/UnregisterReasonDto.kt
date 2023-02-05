package com.mument_android.data.dto.mypage

import java.util.*

data class UnregisterReasonDto(
    val id: Int,
    val userId: Int,
    val profileId: String,
    val leaveCategoryId: Int,
    val leaveCategoryName: String,
    val reasonEtc: String?,
    val createdAt: Date
)
