package com.mument_android.app.domain.entity.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    val userId: String,
    val name: String,
    val profileImage: String
): Parcelable