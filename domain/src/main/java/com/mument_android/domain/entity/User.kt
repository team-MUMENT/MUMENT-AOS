package com.mument_android.domain.entity

import androidx.annotation.Keep

@Keep
data class User(
    val _id: Int,
    val name: String,
    val image: String
)