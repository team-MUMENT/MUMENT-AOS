package com.mument_android.domain.repository.mypage

import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.domain.entity.mypage.UserInfoEntity
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    suspend fun userInfo(): UserInfoEntity
}