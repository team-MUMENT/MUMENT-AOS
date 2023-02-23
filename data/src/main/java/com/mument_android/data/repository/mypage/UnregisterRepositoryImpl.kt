package com.mument_android.data.repository.mypage

import com.mument_android.data.datasource.home.UserLocalDataSource
import com.mument_android.data.datasource.mypage.UnregisterDataSource
import com.mument_android.data.mapper.mypage.UnregisterMapper
import com.mument_android.domain.entity.mypage.UnregisterEntity
import com.mument_android.domain.repository.mypage.UnregisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class UnregisterRepositoryImpl @Inject constructor(
    private val unregisterDataSource: UnregisterDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val unregisterMapper: UnregisterMapper
) : UnregisterRepository {
    override suspend fun fetchUnregisterInfo(): Flow<Boolean> = flow {
        emit(unregisterDataSource.fetchUnregisterInfo())
    }.onEach { success ->
        if (success) {
            userLocalDataSource.deleteLocalData()
        }
    }
}