package com.mument_android.domain.repository.sign

import com.mument_android.domain.entity.sign.SetProfileData
import com.mument_android.domain.entity.sign.SetProfileEntity
import kotlinx.coroutines.flow.Flow


interface SignRepository {
    suspend fun signDupCheck(profileId: String) : Int

    suspend fun signSetProfile(data: SetProfileData) : Flow<SetProfileEntity>
}