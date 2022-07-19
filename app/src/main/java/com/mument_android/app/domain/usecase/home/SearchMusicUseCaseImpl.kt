package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow

class SearchMusicUseCaseImpl(val homeRepository: HomeRepository):SearchMusicUseCase {
    override suspend fun searchMusic(keyword: String): Flow<List<RecentSearchData>> = homeRepository.searchList(keyword)
}