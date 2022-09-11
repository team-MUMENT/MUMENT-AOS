package com.mument_android.app.data.datasource.home

import com.mument_android.app.domain.entity.home.RecentSearchData
import com.mument_android.app.data.network.base.BaseResponse
import com.mument_android.app.data.network.home.HomeService

class RemoteSearchListDataSourceImpl(val homeService: HomeService):RemoteSearchListDataSource {
    override suspend fun searchMusicList(keyword:String): BaseResponse<List<RecentSearchData>> = homeService.searchMusicList(keyword)
}