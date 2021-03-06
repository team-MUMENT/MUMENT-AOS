package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.local.todaymument.TodayMumentDAO
import com.mument_android.app.data.local.todaymument.TodayMumentEntity
import javax.inject.Inject

class LocalTodayMumentDataSourceImpl @Inject constructor(private val dao: TodayMumentDAO) :
    LocalTodayMumentDataSource {
    override suspend fun getTodayMument(userId:String): TodayMumentEntity = dao.getTodayMument(userId)


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