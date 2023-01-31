package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.NoticeListEntity
import com.mument_android.domain.repository.mypage.NoticeListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchNoticeListUseCaseImpl @Inject constructor(
    private val noticeListRepository: NoticeListRepository
):FetchNoticeListUseCase{
    override suspend fun invoke(): Flow<List<NoticeListEntity>> =
        noticeListRepository.fetchNoticeList()

}