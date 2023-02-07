package com.mument_android.data.dto.record

import com.google.gson.annotations.SerializedName

data class ResponseRecordMumentDto(
    @SerializedName("_id")
    val id: String,
    val count: Int
)