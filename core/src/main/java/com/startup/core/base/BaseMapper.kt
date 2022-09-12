package com.startup.core.base

interface BaseMapper<F,T> {
    fun map(from: F): T
}