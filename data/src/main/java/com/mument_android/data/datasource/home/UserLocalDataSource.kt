package com.mument_android.data.datasource.home

interface UserLocalDataSource {
    suspend fun deleteLocalData(): Int
}