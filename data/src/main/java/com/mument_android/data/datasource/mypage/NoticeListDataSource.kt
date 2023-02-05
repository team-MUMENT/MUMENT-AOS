package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.NoticeListDto

interface NoticeListDataSource {
    suspend fun fetchNoticeList(): List<NoticeListDto>?
}