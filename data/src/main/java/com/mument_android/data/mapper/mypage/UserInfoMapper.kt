package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.UserInfoDto
import com.mument_android.domain.entity.mypage.UserInfoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoMapper @Inject constructor() : BaseMapper<UserInfoDto?, UserInfoEntity> {
    override fun map(from: UserInfoDto?): UserInfoEntity = UserInfoEntity(
        id = from?.id,
        userName = from?.userName,
        image = from?.image
    )
}