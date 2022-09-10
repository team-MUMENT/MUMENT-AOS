package com.mument_android.app.domain.usecase.home

import com.mument_android.app.domain.entity.home.recentlist.RecentSearchData
//TODO data layer remove
interface DeleteRecentSearchListUseCase {
    suspend fun deleteRecentSearchItem(data: RecentSearchData)
    suspend fun deleteAllRecentSearchList()
}