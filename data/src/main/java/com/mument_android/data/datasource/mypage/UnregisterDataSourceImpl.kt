package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.RequestUnregisterDto
import com.mument_android.data.network.mypage.MyPageApiService
import javax.inject.Inject

class UnregisterDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService
) : UnregisterDataSource {
    override suspend fun fetchUnregisterInfo(requestUnregisterDto: RequestUnregisterDto): Boolean = kotlin.runCatching {
        myPageApiService.fetchUnregisterInfo(requestUnregisterDto).status == 200
    }.getOrElse { false }

}