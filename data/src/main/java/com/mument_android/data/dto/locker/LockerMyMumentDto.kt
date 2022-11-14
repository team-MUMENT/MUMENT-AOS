package com.mument_android.data.dto.locker

data class LockerMyMumentDto(
    val muments: List<Mument>
) {
    data class Mument(
        val _id: String?,
        val cardTag: List<Int>?,
        val content: String?,
        val createdAt: String?,
        val feelingTag: List<Int>?,
        val impressionTag: List<Int>?,
        val isFirst: Boolean?,
        val isLiked: Boolean?,
        val isPrivate: Boolean?,
        val likeCount: Int?,
        val music: Music?,
        val user: User?,
        val month: Int?,
        val year: Int?
    ) {
        data class Music(
            val _id: String?,
            val artist: String?,
            val image: String?,
            val name: String?
        )

        data class User(
            val _id: String?,
            val image: String?,
            val name: String?
        )
    }
}