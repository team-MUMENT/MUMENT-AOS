package com.mument_android.domain.repository.mypage

import com.mument_android.domain.entity.mypage.NoticeListEntity
import kotlinx.coroutines.flow.Flow

interface NoticeListRepository {
    suspend fun fetchNoticeList(): Flow<List<NoticeListEntity>>
}