package com.mument_android.app.domain.usecase.home

import com.mument_android.app.domain.entity.home.recentlist.RecentSearchData
import com.mument_android.app.domain.repository.home.HomeRepository
import javax.inject.Inject

class DeleteRecentSearchListUseCaseImpl @Inject constructor(private val homeRepository: HomeRepository) :
    DeleteRecentSearchListUseCase {
    override suspend fun deleteRecentSearchItem(data: RecentSearchData) {
        homeRepository.deleteRecentSearchList(data)
    }

    override suspend fun deleteAllRecentSearchList() {
        homeRepository.deleteAllRecentSearchList()
    }
}