package com.mument_android.domain.usecase.home

import kotlinx.coroutines.flow.Flow

interface BeforeWhenHomeEnterUseCase {
    suspend fun checkProfileExist(): Flow<Boolean?> //뭐 더 체크할 수도 있으니 invoke 로는 하지 않았음
}