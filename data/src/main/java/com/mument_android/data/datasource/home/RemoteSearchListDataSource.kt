package com.mument_android.data.datasource.home

import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.entity.home.RecentSearchData

interface RemoteSearchListDataSource {
    suspend fun searchMusicList(keyword:String): ResultWrapper<List<RecentSearchData>?>
}