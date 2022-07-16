package com.mument_android.app.domain.repository.home

import com.mument_android.app.data.local.todaymument.TodayMumentEntity

interface HomeRepository {
    suspend fun getTodayMumentData():List<TodayMumentEntity>

     suspend fun updateMument(mument: TodayMumentEntity)

     suspend fun insertMument(mument: TodayMumentEntity)

    suspend fun deleteMument(mument: TodayMumentEntity)
}