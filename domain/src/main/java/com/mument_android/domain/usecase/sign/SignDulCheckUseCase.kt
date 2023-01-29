package com.mument_android.domain.usecase.sign

interface SignDulCheckUseCase {
    suspend operator fun invoke(userId: String)

}