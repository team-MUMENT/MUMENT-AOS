package com.mument_android.data.datasource.mypage

import com.mument_android.data.network.mypage.MyPageApiService
import javax.inject.Inject

class UnregisterDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService
) : UnregisterDataSource {
    override suspend fun fetchUnregisterInfo():Boolean = kotlin.runCatching {
        myPageApiService.fetchUnregisterInfo().status == 200 }.getOrElse { false }
}