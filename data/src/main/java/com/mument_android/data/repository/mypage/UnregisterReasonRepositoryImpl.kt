package com.mument_android.data.repository.mypage

import com.mument_android.data.datasource.mypage.UnregisterReasonDataSource
import com.mument_android.data.mapper.mypage.RequestUnregisterReasonMapper
import com.mument_android.data.mapper.mypage.UnregisterReasonMapper
import com.mument_android.domain.entity.mypage.RequestUnregisterReasonEntity
import com.mument_android.domain.entity.mypage.UnregisterReasonEntity
import com.mument_android.domain.repository.mypage.UnregisterReasonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UnregisterReasonRepositoryImpl @Inject constructor(
    private val unregisterReasonDataSource: UnregisterReasonDataSource,
    private val unregisterReasonMapper: UnregisterReasonMapper,
    private val requestUnregisterReasonMapper: RequestUnregisterReasonMapper
) : UnregisterReasonRepository {
    override suspend fun postUnregisterReason(
        requestUnregisterReasonEntity: RequestUnregisterReasonEntity
    ): Flow<Boolean> = flow {
        unregisterReasonDataSource.postUnregisterReason(
            requestUnregisterReasonMapper.map(
                requestUnregisterReasonEntity
            )
        )?.let {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)
}