package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.SetProfileData
import com.mument_android.domain.entity.sign.SetProfileEntity
import kotlinx.coroutines.flow.Flow

interface SignPutProfileUseCase {
    suspend fun putProfile(data: SetProfileData) : Flow<SetProfileEntity>
}