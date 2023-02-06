package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.RequestUnregisterReasonDto
import com.mument_android.data.dto.mypage.UnregisterReasonDto
import com.mument_android.data.network.mypage.MyPageApiService
import javax.inject.Inject

class UnregisterReasonDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService
) : UnregisterReasonDataSource {
    override suspend fun postUnregisterReason(
        requestUnregisterReasonDto: RequestUnregisterReasonDto
    ): UnregisterReasonDto? =
        myPageApiService.postUnregisterReason(
            requestUnregisterReasonDto
        ).data

}