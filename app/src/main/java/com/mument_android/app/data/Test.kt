package com.mument_android.app.data

data class Test(
    val `data`: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val muments: List<Mument>
    ) {
        data class Mument(
            val _id: String,
            val cardTag: List<Int>,
            val content: String,
            val createdAt: String,
            val feelingTag: List<Int>,
            val impressionTag: List<Int>,
            val isFirst: Boolean,
            val isLiked: Boolean,
            val isPrivate: Boolean,
            val likeCount: Int,
            val month: Int,
            val music: Music,
            val user: User,
            val year: Int
        ) {
            data class Music(
                val _id: String,
                val artist: String,
                val image: String,
                val name: String
            )

            data class User(
                val _id: String,
                val image: String,
                val name: String
            )
        }
    }
}