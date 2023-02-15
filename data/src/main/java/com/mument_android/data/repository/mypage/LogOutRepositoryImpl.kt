package com.mument_android.data.repository.mypage

import com.mument_android.data.datasource.mypage.LogOutDataSource
import com.mument_android.domain.repository.mypage.LogOutRepository
import javax.inject.Inject

class LogOutRepositoryImpl @Inject constructor(
    private val logOutDataSource: LogOutDataSource) : LogOutRepository {

    override suspend fun logout(): Int {
        logOutDataSource.logout().let {
            return it.code()
        }
    }
}