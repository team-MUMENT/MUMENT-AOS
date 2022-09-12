package com.startup.domain.usecase.home

import com.startup.domain.entity.home.RecentSearchData
import kotlinx.coroutines.flow.Flow

interface SearchMusicUseCase {
    suspend fun searchMusic(keyword:String): Flow<List<RecentSearchData>>
}