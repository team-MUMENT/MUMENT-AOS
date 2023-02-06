package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.data.dto.mypage.UserInfoDto
import com.mument_android.data.network.mypage.MyPageApiService
import javax.inject.Inject

class UserInfoDataSourceImpl @Inject constructor(private val myPageApiService: MyPageApiService) :
    UserInfoDataSource {
    override suspend fun getUserInfo(): UserInfoDto? {
        return myPageApiService.getUserInfo().data
    }
}