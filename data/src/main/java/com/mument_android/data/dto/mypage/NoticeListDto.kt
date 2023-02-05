package com.mument_android.data.dto.mypage

import com.google.gson.annotations.SerializedName

data class NoticeListDto(
    val id: Int,
    val title: String,
    val content: String,
    @SerializedName("created_at")
    val createAt: String
)
