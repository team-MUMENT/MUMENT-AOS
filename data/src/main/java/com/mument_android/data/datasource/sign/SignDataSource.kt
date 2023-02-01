package com.mument_android.data.datasource.sign

import com.mument_android.data.dto.sign.RequestSetProfileDto
import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.data.util.BaseResponse
import retrofit2.Response


interface SignDataSource {
    suspend fun signDupCheck(profileId: String) : Response<Any?>

    suspend fun signPutProfile(requestSetProfileDto: RequestSetProfileDto) : BaseResponse<SetProfileDto>
}