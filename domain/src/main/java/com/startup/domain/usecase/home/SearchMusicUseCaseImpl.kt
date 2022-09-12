package com.startup.domain.usecase.home

import com.startup.domain.entity.home.RecentSearchData
import com.startup.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMusicUseCaseImpl  @Inject constructor(val homeRepository: HomeRepository):SearchMusicUseCase {
    override suspend fun searchMusic(keyword: String): Flow<List<RecentSearchData>> = homeRepository.searchList(keyword)
}