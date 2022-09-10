package com.mument_android.app.domain.usecase.home

import com.mument_android.app.domain.entity.home.recentlist.RecentSearchData
//TODO data layer remove
import kotlinx.coroutines.flow.Flow

interface SearchMusicUseCase {
    suspend fun searchMusic(keyword:String): Flow<List<RecentSearchData>>
}