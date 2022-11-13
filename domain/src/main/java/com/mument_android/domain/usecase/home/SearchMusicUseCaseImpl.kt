package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMusicUseCaseImpl  @Inject constructor(val homeRepository: HomeRepository):SearchMusicUseCase {
    override suspend fun searchMusic(keyword: String): Flow<List<RecentSearchData>> = homeRepository.searchList(keyword)
}