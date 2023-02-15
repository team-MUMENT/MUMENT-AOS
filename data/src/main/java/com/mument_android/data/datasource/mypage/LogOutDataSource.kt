package com.mument_android.data.datasource.mypage

import retrofit2.Response

interface LogOutDataSource {
    suspend fun logout() : Response<Any?>
}