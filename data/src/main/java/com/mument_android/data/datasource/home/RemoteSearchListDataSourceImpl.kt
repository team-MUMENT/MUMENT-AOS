package com.mument_android.data.datasource.home

import com.mument_android.core.network.ApiResult
import com.mument_android.data.network.home.HomeService
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.data.util.BaseResponse

class RemoteSearchListDataSourceImpl(val homeService: HomeService) : RemoteSearchListDataSource {
    override suspend fun searchMusicList(keyword: String): ApiResult<List<RecentSearchData>?> {
        val data = homeService.searchMusicList(keyword)
        return if (data.success && data.data != null) {
            ApiResult.Success(data.data)
        } else {
            ApiResult.Failure(throwable = Throwable(data.message))
        }
    }
}