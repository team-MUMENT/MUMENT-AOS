package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CRURecentSearchListUseCaseImpl @Inject constructor(private val homeRepository: HomeRepository) :
    CRURecentSearchListUseCase {
    override suspend fun insertOrUpdateRecentSearchItem(data: RecentSearchData) {
        homeRepository.insertRecentSearchList(data)
    }

    override suspend fun getAllRecentSearchList(): Flow<List<RecentSearchData>?> =
        flow { homeRepository.getRecentSearchList()?.let { emit(it) } }
}