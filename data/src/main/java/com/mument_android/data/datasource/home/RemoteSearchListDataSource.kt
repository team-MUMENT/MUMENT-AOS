package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.RecentSearchDataDto
import com.mument_android.data.util.ResultWrapper

interface RemoteSearchListDataSource {
    suspend fun searchMusicList(keyword:String): ResultWrapper<RecentSearchDataDto?>
}