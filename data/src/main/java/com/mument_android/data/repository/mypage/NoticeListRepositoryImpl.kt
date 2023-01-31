package com.mument_android.data.repository.mypage

import com.mument_android.data.datasource.mypage.NoticeListDataSource
import com.mument_android.data.mapper.mypage.NoticeListMapper
import com.mument_android.domain.entity.mypage.NoticeListEntity
import com.mument_android.domain.repository.mypage.NoticeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoticeListRepositoryImpl @Inject constructor(
    private val noticeListDataSource: NoticeListDataSource,
    private val noticeListMapper: NoticeListMapper
) : NoticeListRepository {
    override suspend fun fetchNoticeList(
    ): Flow<List<NoticeListEntity>> = flow {
        noticeListDataSource.fetchNoticeList()?.let {
            emit(noticeListMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)

}