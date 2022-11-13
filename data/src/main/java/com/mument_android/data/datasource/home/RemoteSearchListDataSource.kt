package com.mument_android.data.datasource.home

import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.data.util.BaseResponse

interface RemoteSearchListDataSource {
    suspend fun searchMusicList(keyword:String): BaseResponse<List<RecentSearchData>>
}