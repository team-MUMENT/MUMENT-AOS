package com.mument_android.app.domain.usecase.main

interface LikeMumentUseCase {
    suspend operator fun invoke(mumentId: Int, userId: Int)
}