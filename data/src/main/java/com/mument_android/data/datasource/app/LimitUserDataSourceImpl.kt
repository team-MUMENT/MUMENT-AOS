package com.mument_android.data.datasource.app

import com.mument_android.data.dto.LimitUserDto
import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.data.network.app.AppApiService
import com.mument_android.data.network.mypage.MyPageApiService
import javax.inject.Inject

class LimitUserDataSourceImpl @Inject constructor(private val appApiService: AppApiService) :
    LimitUserDataSource {

    override suspend fun limitUser(): LimitUserDto? {
        return appApiService.limitUser().data
    }
}