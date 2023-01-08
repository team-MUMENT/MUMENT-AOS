package com.mument_android.data.datasource.home

import com.mument_android.data.local.todaymument.TodayMumentDAO
import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.entity.home.TodayMumentEntity
import java.time.LocalDate
import javax.inject.Inject

class LocalTodayMumentDataSourceImpl @Inject constructor(private val dao: TodayMumentDAO) :
    LocalTodayMumentDataSource {
    override suspend fun getTodayMument(userId: String): ResultWrapper<TodayMumentEntity> =
        runCatching {
            dao.getTodayMument(userId).run {//DB에 없거나 오늘이 아닐 때
                if (this == null || this.todayDate == LocalDate.now().toString()) { //여기서 처리하는게 좀 맘에 걸림
                    ResultWrapper.LocalError("Empty")
                } else {
                    ResultWrapper.Success(this)
                }
            }
        }.getOrElse { throwable ->
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