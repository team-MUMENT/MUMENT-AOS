package com.mument_android.data.datasource.sign

import retrofit2.Response


interface SignDataSource {
    suspend fun signDupCheck(profileId: String) : Response<Any?>
}