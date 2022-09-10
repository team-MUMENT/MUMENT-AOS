package com.mument_android.app.domain.usecase.home

import com.mument_android.app.domain.entity.home.recentlist.RecentSearchData
//TODO data layer remove
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMusicUseCaseImpl  @Inject constructor(val homeRepository: HomeRepository):SearchMusicUseCase {
    override suspend fun searchMusic(keyword: String): Flow<List<RecentSearchData>> = homeRepository.searchList(keyword)
}