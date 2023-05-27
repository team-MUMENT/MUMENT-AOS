package com.mument_android.data.repository.mypage

import com.mument_android.data.datasource.mypage.UserInfoDataSource
import com.mument_android.data.mapper.mypage.UserInfoMapper
import com.mument_android.domain.entity.mypage.UserInfoEntity
import com.mument_android.domain.repository.mypage.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoDataSource: UserInfoDataSource,
    private val userInfoMapper: UserInfoMapper
): UserInfoRepository {
    override suspend fun userInfo(): UserInfoEntity {
        userInfoDataSource.getUserInfo().let {
            return userInfoMapper.map(it.data)
        }
    }

}