package com.mument_android.app.domain.usecase.home

import com.mument_android.app.domain.entity.home.recentlist.RecentSearchData
//TODO data layer remove
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CRURecentSearchListUseCaseImpl @Inject constructor(private val homeRepository: HomeRepository):CRURecentSearchListUseCase {
    override suspend fun insertOrUpdateRecentSearchItem(data: RecentSearchData) {
        homeRepository.insertRecentSearchList(data)
    }

    override suspend fun getAllRecentSearchList(): Flow<List<RecentSearchData>> = homeRepository.getRecentSearchList()
}