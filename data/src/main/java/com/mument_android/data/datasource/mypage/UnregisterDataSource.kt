package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.UnregisterDto

interface UnregisterDataSource {
    suspend fun fetchUnregisterInfo(): UnregisterDto?
}