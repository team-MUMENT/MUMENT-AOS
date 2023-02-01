package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.NoticeListEntity
import kotlinx.coroutines.flow.Flow

interface FetchNoticeDetailUseCase {
    suspend operator fun invoke(noticeId: Int): Flow<NoticeListEntity>
}