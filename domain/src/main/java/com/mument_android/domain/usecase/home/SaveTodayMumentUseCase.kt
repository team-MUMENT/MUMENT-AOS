package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.TodayMumentEntity
import kotlinx.coroutines.flow.Flow

interface SaveTodayMumentUseCase {
    suspend fun saveTodayMument(mument: TodayMumentEntity)
    suspend fun getTodayMument(userId:String):Flow<TodayMumentEntity>
}