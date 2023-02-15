package com.mument_android.data.datasource.mypage

import com.mument_android.data.network.mypage.MyPageApiService
import retrofit2.Response
import javax.inject.Inject

class LogOutDataSourceImpl @Inject constructor(private val myPageApiService: MyPageApiService) :
    LogOutDataSource {
    override suspend fun logout(): Response<Any?> {
        return myPageApiService.postLogOut()
    }
}