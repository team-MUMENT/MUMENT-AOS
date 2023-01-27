package com.mument_android.data.util

data class BaseResponse<T> (
    val status: Int,
    val success: Boolean,
    val message: String?,
    val data: T? = null
)