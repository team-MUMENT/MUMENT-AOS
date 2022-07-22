package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.local.todaymument.TodayMumentEntity

interface LocalTodayMumentDataSource {
    suspend fun getTodayMument(userId:String):TodayMumentEntity
    suspend fun updateMument(mument:TodayMumentEntity)
    suspend fun insertMument(mument:TodayMumentEntity)
    suspend fun deleteMument(mument:TodayMumentEntity)
}