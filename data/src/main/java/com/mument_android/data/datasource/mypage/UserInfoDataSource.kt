package com.mument_android.data.datasource.mypage

import com.mument_android.data.dto.mypage.UserInfoDto

interface UserInfoDataSource {
    suspend fun getUserInfo() : UserInfoDto?
}