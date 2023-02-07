package com.mument_android.domain.repository.mypage

import com.mument_android.domain.entity.mypage.UserInfoEntity

interface UserInfoRepository {
    suspend fun userInfo(): UserInfoEntity
}