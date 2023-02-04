package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.data.network.mypage.MyPageApiService
import javax.inject.Inject

class NoticeDetailDataSourceImpl @Inject constructor(private val myPageApiService: MyPageApiService) :
    NoticeDetailDataSource {
    override suspend fun fetchNoticeDetail(noticeId: Int): NoticeListDto? {
        return myPageApiService.fetchNoticeDetail(noticeId).data
    }
}