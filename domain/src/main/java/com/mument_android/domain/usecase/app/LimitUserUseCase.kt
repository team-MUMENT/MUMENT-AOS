package com.mument_android.domain.usecase.app

import com.mument_android.domain.entity.LimitUserEntity
import com.mument_android.domain.entity.mypage.NoticeListEntity
import kotlinx.coroutines.flow.Flow

interface LimitUserUseCase {
    suspend operator fun invoke(): LimitUserEntity?
}