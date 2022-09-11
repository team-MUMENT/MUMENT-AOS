package com.mument_android.app.data.datasource.home

import com.mument_android.app.domain.entity.home.RecentSearchData
import com.mument_android.app.data.network.base.BaseResponse

interface RemoteSearchListDataSource {
    suspend fun searchMusicList(keyword:String):BaseResponse<List<RecentSearchData>>
}