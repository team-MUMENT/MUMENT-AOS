package com.mument_android.data.datasource.sign

import com.mument_android.data.dto.sign.SignDupCheckDto
import com.mument_android.data.util.BaseResponse

interface SignDataSource {
    suspend fun signDupCheck(profileId: String) : BaseResponse<SignDupCheckDto>
}