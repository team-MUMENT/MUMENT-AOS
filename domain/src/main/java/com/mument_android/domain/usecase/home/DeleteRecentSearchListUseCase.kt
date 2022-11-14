package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.RecentSearchData
interface DeleteRecentSearchListUseCase {
    suspend fun deleteRecentSearchItem(data: RecentSearchData)
    suspend fun deleteAllRecentSearchList()
}