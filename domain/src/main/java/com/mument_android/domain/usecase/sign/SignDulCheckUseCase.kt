package com.mument_android.domain.usecase.sign

interface SignDulCheckUseCase {
    suspend fun dupCheckNickname(userId: String) : Int
}