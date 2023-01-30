package com.mument_android.data.datasource.sign

import com.mument_android.data.network.sign.SignApiService
import retrofit2.Response
import javax.inject.Inject

class SignDataSourceImpl @Inject constructor(
    private val signApiService: SignApiService
) : SignDataSource {
    override suspend fun signDupCheck(profileId: String): Response<Any?> {
        return signApiService.signDuplicationCheck(profileId)
    }
}