package com.mument_android.data.dto.history

data class MumentHistoryDto(
    val status: Int, val success: String, val message: String, val data: MumentHistoryItem?
)
