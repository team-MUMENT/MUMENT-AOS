package com.mument_android.domain.entity.user

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class UserEntity(
    val userId: String,
    val name: String,
    val profileImage: String?
): Parcelable