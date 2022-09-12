package com.startup.domain.usecase.home

import com.startup.domain.entity.home.TodayMumentEntity
import kotlinx.coroutines.flow.Flow

interface SaveTodayMumentUseCase {
    suspend fun saveTodayMument(mument: TodayMumentEntity)
    suspend fun getTodayMument(userId:String):Flow<TodayMumentEntity>
}