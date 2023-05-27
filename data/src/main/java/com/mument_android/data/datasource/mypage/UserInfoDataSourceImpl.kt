package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.UserInfoDto
import com.mument_android.data.network.mypage.MyPageApiService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class UserInfoDataSourceImpl @Inject constructor(
    private val myPageApiService: MyPageApiService
    ) : UserInfoDataSource {
    override suspend fun getUserInfo(): BaseResponse<UserInfoDto> {
        return myPageApiService.getUserInfo()
    }
}