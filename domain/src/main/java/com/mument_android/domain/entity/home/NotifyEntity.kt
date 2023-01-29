package com.mument_android.domain.entity.home

import java.time.LocalDate

data class NotifyEntity(
    val id: String,
    val createdAt: LocalDate,
    val image: String,
    val newsId: Int,
    val title: String,
    val type: String
)