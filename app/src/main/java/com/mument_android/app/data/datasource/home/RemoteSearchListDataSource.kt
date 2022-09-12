package com.mument_android.app.data.datasource.home

import com.startup.domain.entity.home.RecentSearchData
import com.startup.core.base.BaseResponse

interface RemoteSearchListDataSource {
    suspend fun searchMusicList(keyword:String): BaseResponse<List<RecentSearchData>>
}