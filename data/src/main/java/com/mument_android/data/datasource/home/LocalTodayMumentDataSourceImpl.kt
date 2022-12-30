package com.mument_android.data.datasource.home

import com.mument_android.data.local.todaymument.TodayMumentDAO
import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.entity.home.TodayMumentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalTodayMumentDataSourceImpl @Inject constructor(private val dao: TodayMumentDAO) :
    LocalTodayMumentDataSource {
    override suspend fun getTodayMument(userId: String): Flow<ResultWrapper<TodayMumentEntity?>> =
        flow { emit(ResultWrapper.Success(dao.getTodayMument(userId))) }.catch { throwable ->
            ResultWrapper.LocalError(throwable.message)
        }

    override suspend fun updateMument(mument: TodayMumentEntity) {
        dao.updateTodayMument(mument)
    }

    override suspend fun insertMument(mument: TodayMumentEntity) {
        dao.insertToDayMument(mument)
    }

    override suspend fun deleteMument(mument: TodayMumentEntity) {
        dao.deleteTodayMument(mument)
    }
}