package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.NoticeListEntity
import kotlinx.coroutines.flow.Flow

interface FetchNoticeListUseCase {
    suspend operator fun invoke(): Flow<List<NoticeListEntity>>
}