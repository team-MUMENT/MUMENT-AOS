package com.mument_android.data.repository.mypage

import com.mument_android.data.datasource.mypage.UnregisterDataSource
import com.mument_android.data.mapper.mypage.UnregisterMapper
import com.mument_android.domain.entity.mypage.UnregisterEntity
import com.mument_android.domain.repository.mypage.UnregisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UnregisterRepositoryImpl @Inject constructor(
    private val unregisterDataSource: UnregisterDataSource,
    private val unregisterMapper: UnregisterMapper
) : UnregisterRepository {
    override suspend fun fetchUnregisterInfo(): Flow<Boolean> = flow {
        unregisterDataSource.fetchUnregisterInfo()?.let {
            emit(it)
        }
    }
}