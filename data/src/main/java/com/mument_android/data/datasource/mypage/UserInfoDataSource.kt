package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.UserInfoDto
import com.mument_android.data.util.BaseResponse

interface UserInfoDataSource {
    suspend fun getUserInfo() : BaseResponse<UserInfoDto>
}