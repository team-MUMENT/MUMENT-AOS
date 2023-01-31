package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.data.network.mypage.MyPageApiService
import javax.inject.Inject

class NoticeListDataSourceImpl @Inject constructor(private val myPageApiService: MyPageApiService) :
    NoticeListDataSource {
    override suspend fun fetchNoticeList(): List<NoticeListDto>? =
        myPageApiService.fetchNoticeList().data
}