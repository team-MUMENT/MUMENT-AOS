package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.RecentSearchDataDto
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall

class RemoteSearchListDataSourceImpl(val homeService: HomeService) : RemoteSearchListDataSource {
    override suspend fun searchMusicList(keyword: String): ResultWrapper<RecentSearchDataDto?> =
        catchingApiCall { homeService.searchMusicList(keyword) }
}