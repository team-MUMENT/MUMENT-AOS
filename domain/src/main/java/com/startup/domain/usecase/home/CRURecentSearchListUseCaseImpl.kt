package com.startup.domain.usecase.home

import com.startup.domain.entity.home.RecentSearchData
import com.startup.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CRURecentSearchListUseCaseImpl @Inject constructor(private val homeRepository: HomeRepository):CRURecentSearchListUseCase {
    override suspend fun insertOrUpdateRecentSearchItem(data: RecentSearchData) {
        homeRepository.insertRecentSearchList(data)
    }

    override suspend fun getAllRecentSearchList(): Flow<List<RecentSearchData>> = homeRepository.getRecentSearchList()
}