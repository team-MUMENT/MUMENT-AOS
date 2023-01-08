package com.mument_android.data.datasource.home

import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.entity.home.TodayMumentEntity
import kotlinx.coroutines.flow.Flow

interface LocalTodayMumentDataSource {
    suspend fun getTodayMument(userId: String): ResultWrapper<TodayMumentEntity>
    suspend fun updateMument(mument: TodayMumentEntity)
    suspend fun insertMument(mument: TodayMumentEntity)
    suspend fun deleteMument(mument: TodayMumentEntity)
}