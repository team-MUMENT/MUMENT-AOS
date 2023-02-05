package com.mument_android.data.repository.mypage

import com.mument_android.data.datasource.mypage.UnregisterReasonDataSource
import com.mument_android.data.mapper.mypage.UnregisterReasonMapper
import com.mument_android.domain.entity.mypage.UnregisterReasonEntity
import com.mument_android.domain.repository.mypage.UnregisterReasonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UnregisterReasonRepositoryImpl @Inject constructor(
    private val unregisterReasonDataSource: UnregisterReasonDataSource,
    private val unregisterReasonMapper: UnregisterReasonMapper
) : UnregisterReasonRepository {
    override suspend fun postUnregisterReason(
        leaveCategoryId: Int,
        reasonEtc: String
    ): Flow<UnregisterReasonEntity> = flow {
        unregisterReasonDataSource.postUnregisterReason(leaveCategoryId, reasonEtc)?.let {
            emit(unregisterReasonMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)
}