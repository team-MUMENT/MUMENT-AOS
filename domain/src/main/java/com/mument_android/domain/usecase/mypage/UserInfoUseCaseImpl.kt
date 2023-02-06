package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.UserInfoEntity
import com.mument_android.domain.repository.mypage.UserInfoRepository
import javax.inject.Inject

class UserInfoUseCaseImpl @Inject constructor(
    private val userInfoRepository: UserInfoRepository
): UserInfoUseCase {
    override suspend fun invoke(): UserInfoEntity {
        return userInfoRepository.userInfo()
    }
}

