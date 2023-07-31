package com.mument_android.data.usecase.home

import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.repository.home.HomeRepository
import com.mument_android.domain.usecase.home.SearchMusicUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMusicUseCaseImpl @Inject constructor(val homeRepository: HomeRepository) :
    SearchMusicUseCase {
    override suspend fun searchMusic(keyword: String): Flow<List<RecentSearchData>?> =
        flow { emit(homeRepository.searchList(keyword)) }
}