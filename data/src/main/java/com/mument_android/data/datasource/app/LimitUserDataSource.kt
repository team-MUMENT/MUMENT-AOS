package com.mument_android.data.datasource.app

import com.mument_android.data.dto.LimitUserDto

interface LimitUserDataSource {
    suspend fun limitUser(): LimitUserDto?
}