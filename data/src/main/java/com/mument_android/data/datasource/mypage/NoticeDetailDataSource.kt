package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.NoticeListDto

interface NoticeDetailDataSource {
    suspend fun fetchNoticeDetail(noticeId: Int): NoticeListDto?
}