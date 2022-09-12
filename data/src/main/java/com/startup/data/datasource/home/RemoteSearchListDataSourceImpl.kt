package com.startup.data.datasource.home

import com.startup.data.network.home.HomeService
import com.startup.domain.entity.home.RecentSearchData
import com.startup.core.base.BaseResponse

class RemoteSearchListDataSourceImpl(val homeService: HomeService): RemoteSearchListDataSource {
    override suspend fun searchMusicList(keyword:String): BaseResponse<List<RecentSearchData>> = homeService.searchMusicList(keyword)
}