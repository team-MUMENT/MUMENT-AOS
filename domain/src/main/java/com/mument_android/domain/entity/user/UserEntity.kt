package com.mument_android.domain.entity.user

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class UserEntity(
    val userId: String,
    val name: String,
    val profileImage: String?
): Serializable