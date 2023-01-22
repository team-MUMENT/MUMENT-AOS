package com.mument_android.home.models

import com.mument_android.home.util.NotifyType

data class Notify(
    val id: String,
    val createdAt: String,
    val image: String,
    val newsId: Int,
    val title: String,
    val notifyType: NotifyType
)