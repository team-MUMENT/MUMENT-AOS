package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.local.recentlist.RecentSearchData

interface DeleteRecentSearchListUseCase {
    suspend fun deleteRecentSearchItem(data:RecentSearchData)
    suspend fun deleteAllRecentSearchList()
}