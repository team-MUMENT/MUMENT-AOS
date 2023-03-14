package com.mument_android.data.dto.home

import androidx.annotation.Keep
import com.mument_android.domain.entity.home.Like
import com.mument_android.domain.entity.home.Notice

@Keep
data class NotifyItem(
    val id: Int,
    val type: String,
    val userId: Int,
    val isDeleted: Boolean,
    val isRead: Boolean,
    val createdAt: String,
    val linkId: Int,  //mument Id
    val notice: Notice,
    val like: Like
)