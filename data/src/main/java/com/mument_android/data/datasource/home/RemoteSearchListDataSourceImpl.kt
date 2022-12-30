package com.mument_android.data.datasource.home

import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall
import com.mument_android.domain.entity.home.RecentSearchData

class RemoteSearchListDataSourceImpl(val homeService: HomeService) : RemoteSearchListDataSource {
    override suspend fun searchMusicList(keyword: String): ResultWrapper<List<RecentSearchData>?> =
        catchingApiCall { homeService.searchMusicList(keyword) }
}