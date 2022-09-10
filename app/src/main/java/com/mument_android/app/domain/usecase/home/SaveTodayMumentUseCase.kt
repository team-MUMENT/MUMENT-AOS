package com.mument_android.app.domain.usecase.home

import com.mument_android.app.domain.entity.home.todaymument.TodayMumentEntity
//TODO data layer remove
import kotlinx.coroutines.flow.Flow

interface SaveTodayMumentUseCase {
    suspend fun saveTodayMument(mument: TodayMumentEntity)
    suspend fun getTodayMument(userId:String):Flow<TodayMumentEntity>
}