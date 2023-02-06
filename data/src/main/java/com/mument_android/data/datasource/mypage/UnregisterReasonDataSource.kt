package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.RequestUnregisterReasonDto
import com.mument_android.data.dto.mypage.UnregisterReasonDto

interface UnregisterReasonDataSource {
    suspend fun postUnregisterReason(requestUnregisterReasonDto: RequestUnregisterReasonDto): UnregisterReasonDto?
}