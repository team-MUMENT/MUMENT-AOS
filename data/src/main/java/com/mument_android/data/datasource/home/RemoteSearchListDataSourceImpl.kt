package com.mument_android.data.datasource.home

import com.mument_android.data.network.home.HomeService
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.data.util.BaseResponse

class RemoteSearchListDataSourceImpl(val homeService: HomeService): RemoteSearchListDataSource {
    override suspend fun searchMusicList(keyword:String): BaseResponse<List<RecentSearchData>> = homeService.searchMusicList(keyword)
}