package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.local.todaymument.TodayMumentEntity

interface TodayMumentDataSource {
    suspend fun getTodayMument():List<TodayMumentEntity>
    suspend fun updateMument(mument:TodayMumentEntity)
    suspend fun insertMument(mument:TodayMumentEntity)
    suspend fun deleteMument(mument:TodayMumentEntity)
}