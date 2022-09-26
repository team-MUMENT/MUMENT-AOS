package com.mument_android.core.base

interface BaseMapper<F,T> {
    fun map(from: F): T
}