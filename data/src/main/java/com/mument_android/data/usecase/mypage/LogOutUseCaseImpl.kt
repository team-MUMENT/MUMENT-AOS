package com.mument_android.data.usecase.mypage

import com.mument_android.domain.repository.mypage.LogOutRepository
import com.mument_android.domain.usecase.mypage.LogOutUseCase
import javax.inject.Inject

class LogOutUseCaseImpl @Inject constructor(
    private val logOutRepository: LogOutRepository
) : LogOutUseCase {
    override suspend fun logout(): Int {
        return logOutRepository.logout()
    }
}