package com.mument_android.domain.repository.app

import com.mument_android.domain.entity.LimitUserEntity

interface LimitUserRepository {
    suspend fun limitUser(): LimitUserEntity
}