package com.mument_android.domain.repository.sign


interface SignRepository {
    suspend fun signDupCheck(profileId: String)
}