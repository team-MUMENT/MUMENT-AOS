package com.mument_android.app.domain.usecase.home

import com.mument_android.app.domain.entity.home.RecentSearchData
import kotlinx.coroutines.flow.Flow

interface CRURecentSearchListUseCase {

    suspend fun insertOrUpdateRecentSearchItem(data: RecentSearchData)
    suspend fun getAllRecentSearchList(): Flow<List<RecentSearchData>>
}