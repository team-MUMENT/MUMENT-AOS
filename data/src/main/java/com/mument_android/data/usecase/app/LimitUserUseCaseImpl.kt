package com.mument_android.data.usecase.app

import com.mument_android.domain.entity.LimitUserEntity
import com.mument_android.domain.repository.app.LimitUserRepository
import com.mument_android.domain.usecase.app.LimitUserUseCase
import javax.inject.Inject

class LimitUserUseCaseImpl @Inject constructor(
    private val limitUserRepository: LimitUserRepository
): LimitUserUseCase {
    override suspend fun invoke(): LimitUserEntity =
        limitUserRepository.limitUser()

}