package com.mument_android.domain.entity.mypage

data class UnregisterEntity(
    val id: Int,
    val profileId: String,
    val isDeleted: Boolean,
    val updatedAt: String
)



