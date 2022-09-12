package com.startup.core.base

data class BaseResponse<T> (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T?
)