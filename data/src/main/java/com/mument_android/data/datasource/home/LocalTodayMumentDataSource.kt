package com.mument_android.data.datasource.home

import com.mument_android.data.local.todaymument.TodayMumentEntity
import com.mument_android.data.util.ResultWrapper

interface LocalTodayMumentDataSource {
    suspend fun getTodayMument(): ResultWrapper<TodayMumentEntity>
    suspend fun updateMument(mument: TodayMumentEntity)
    suspend fun insertMument(mument: TodayMumentEntity)
    suspend fun deleteMument(mument: TodayMumentEntity)
}