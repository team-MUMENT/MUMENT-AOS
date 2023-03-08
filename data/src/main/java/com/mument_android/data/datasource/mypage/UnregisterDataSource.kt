package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.RequestUnregisterDto

interface UnregisterDataSource {
    suspend fun fetchUnregisterInfo(requestUnregisterDto: RequestUnregisterDto): Boolean
}