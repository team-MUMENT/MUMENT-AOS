package com.mument_android.data.dto

import com.mument_android.domain.entity.user.UserEntity

data class TempUserDto(
    val id: Int,
    val userName: String,
    val image: String?
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            userId = id.toString(),
            name = userName,
            profileImage = image
        )
    }
}