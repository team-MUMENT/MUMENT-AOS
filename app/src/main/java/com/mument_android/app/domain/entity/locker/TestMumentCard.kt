package com.mument_android.app.domain.entity.locker

data class TestMumentCard(
    val _id: String,
    val cardTag: List<Int>,
    val content: String,
    val createdAt: String,
    val feelingTag : List<Int>,
    val impressionTag : List<Int>,
    val music_Id: String,
    val musicImage: String,
    val musicName: String,
    val musicArtist: String,
    val user_Id: String,
    val userImage: String,
    val userName: String,
    val likeCount: Int?,
    val isLiked: Boolean?,
    val isPrivate: Boolean?,
    val isFirst: Boolean?,
    val month : Int,
    val year : Int
)
