package com.startup.domain.usecase.home

import com.startup.domain.entity.home.RecentSearchData
import kotlinx.coroutines.flow.Flow

interface CRURecentSearchListUseCase {

    suspend fun insertOrUpdateRecentSearchItem(data: RecentSearchData)
    suspend fun getAllRecentSearchList(): Flow<List<RecentSearchData>>
}