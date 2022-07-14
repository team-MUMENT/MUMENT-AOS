package com.mument_android.app.domain.entity

import com.mument_android.app.domain.entity.MumentCardData.Music
import com.mument_android.app.domain.entity.MumentCardData.User

data class LockerMumentEntity(
    val date: String,
    val mumentCard: List<MumentLockerCard>
) {
    data class MumentLockerCard(
        val id: String,
        val content: String,
        val music: Music,
        val user: User,
        val likeCount: Int?,
        val isPrivate: Boolean?,
        val isFirst: Boolean?,
        val impression: List<Int>?,
        val feeling: List<Int>?,
        val createdAt: String
    )
}