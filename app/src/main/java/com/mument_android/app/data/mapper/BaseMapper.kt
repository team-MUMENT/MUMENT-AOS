package com.mument_android.app.data.mapper

interface BaseMapper<F,T> {
    fun map(from: F): T
}