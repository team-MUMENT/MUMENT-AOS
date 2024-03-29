package com.mument_android.domain.entity.locker

data class LockerMumentEntity(
    val date: String?,
    val mumentCard: List<MumentLockerCard>?,
    var isOther: Boolean? = null
) {
    data class MumentLockerCard(
        val _id: String?,
        val cardTag: List<Int>?,
        val content: String?,
        val createdAt: String?,
        val feelingTag: List<Int>?,
        val impressionTag: List<Int>?,
        val music_Id: String?,
        val musicImage: String?,
        val musicName: String?,
        val musicArtist: String?,
        val user_Id: String?,
        val userImage: String?,
        val userName: String?,
        var likeCount: Int?,
        var isLiked: Boolean?,
        val isPrivate: Boolean?,
        val isFirst: Boolean?,
        val month: Int?,
        val year: Int?
    )
}