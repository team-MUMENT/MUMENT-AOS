package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.local.recentlist.RecentSearchData
import kotlinx.coroutines.flow.Flow

interface SearchMusicUseCase {
    suspend fun searchMusic(keyword:String): Flow<List<RecentSearchData>>
}