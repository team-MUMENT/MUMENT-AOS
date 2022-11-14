package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.RecentSearchData
import kotlinx.coroutines.flow.Flow

interface SearchMusicUseCase {
    suspend fun searchMusic(keyword:String): Flow<List<RecentSearchData>>
}