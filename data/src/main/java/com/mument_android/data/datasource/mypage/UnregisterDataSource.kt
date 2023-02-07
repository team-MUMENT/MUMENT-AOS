package com.mument_android.data.datasource.mypage

interface UnregisterDataSource {
    suspend fun fetchUnregisterInfo(): Boolean
}