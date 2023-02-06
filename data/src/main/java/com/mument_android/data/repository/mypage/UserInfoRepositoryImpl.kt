package com.mument_android.data.repository.mypage

import com.mument_android.data.datasource.mypage.UserInfoDataSource
import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.mypage.UserInfoMapper
import com.mument_android.data.mapper.sign.RequestSetProfileMapper
import com.mument_android.data.mapper.sign.SetProfileMapper
import com.mument_android.data.mapper.sign.SignMapper
import com.mument_android.domain.entity.mypage.UserInfoEntity
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.repository.mypage.UserInfoRepository
import com.mument_android.domain.repository.sign.SignRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoDataSource: UserInfoDataSource,
    private val userInfoMapper: UserInfoMapper
): UserInfoRepository {
    override suspend fun fetchBlockUserList(): UserInfoEntity {
        userInfoDataSource.getUserInfo().let {
            return userInfoMapper.map(it)
        }
    }

}