package com.mument_android.data.datasource.home

import com.mument_android.domain.entity.home.TodayMumentEntity

interface LocalTodayMumentDataSource {
    suspend fun getTodayMument(userId:String): TodayMumentEntity
    suspend fun updateMument(mument: TodayMumentEntity)
    suspend fun insertMument(mument: TodayMumentEntity)
    suspend fun deleteMument(mument: TodayMumentEntity)
}