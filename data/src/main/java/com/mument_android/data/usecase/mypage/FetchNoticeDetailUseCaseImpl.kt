package com.mument_android.data.usecase.mypage

import com.mument_android.domain.entity.mypage.NoticeListEntity
import com.mument_android.domain.repository.mypage.NoticeListRepository
import com.mument_android.domain.usecase.mypage.FetchNoticeDetailUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchNoticeDetailUseCaseImpl @Inject constructor(
    private val noticeListRepository: NoticeListRepository
) : FetchNoticeDetailUseCase {
    override suspend operator fun invoke(noticeId: Int): Flow<NoticeListEntity> =
        noticeListRepository.fetchNoticeDetail(noticeId)
}