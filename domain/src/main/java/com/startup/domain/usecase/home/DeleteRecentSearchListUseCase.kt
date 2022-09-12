package com.startup.domain.usecase.home

import com.startup.domain.entity.home.RecentSearchData
interface DeleteRecentSearchListUseCase {
    suspend fun deleteRecentSearchItem(data: RecentSearchData)
    suspend fun deleteAllRecentSearchList()
}