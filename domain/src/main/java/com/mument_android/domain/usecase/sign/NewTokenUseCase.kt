package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.NewTokenEntity

interface NewTokenUseCase {
    suspend fun newToken() : NewTokenEntity?
}