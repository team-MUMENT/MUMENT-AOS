package com.mument_android.app.data.network.base

data class BaseResponse<T> (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T
)