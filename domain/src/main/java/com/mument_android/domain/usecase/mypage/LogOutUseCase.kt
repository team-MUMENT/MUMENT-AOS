package com.mument_android.domain.usecase.mypage

interface LogOutUseCase {
    suspend fun logout() : Int
}