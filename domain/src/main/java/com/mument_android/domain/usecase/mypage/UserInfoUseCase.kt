package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.UserInfoEntity

interface UserInfoUseCase {
    suspend operator fun invoke(): UserInfoEntity
}