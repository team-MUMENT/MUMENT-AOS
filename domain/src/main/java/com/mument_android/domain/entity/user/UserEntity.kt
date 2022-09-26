package com.mument_android.domain.entity.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val userId: String,
    val name: String,
    val profileImage: String?
): Parcelable