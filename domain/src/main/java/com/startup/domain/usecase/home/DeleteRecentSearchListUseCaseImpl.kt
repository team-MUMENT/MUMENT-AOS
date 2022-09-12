package com.startup.domain.usecase.home

import com.startup.domain.entity.home.RecentSearchData
import com.startup.domain.repository.home.HomeRepository
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