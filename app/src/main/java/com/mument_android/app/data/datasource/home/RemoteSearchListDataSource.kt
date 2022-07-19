package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.network.base.BaseResponse

interface RemoteSearchListDataSource {
    suspend fun searchMusicList(keyword:String):BaseResponse<List<RecentSearchData>>
}