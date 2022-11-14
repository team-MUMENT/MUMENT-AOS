package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.repository.home.HomeRepository
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