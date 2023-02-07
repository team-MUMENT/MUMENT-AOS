package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.data.dto.mypage.UserInfoDto
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.domain.entity.mypage.UserInfoEntity

class UserInfoMapper : BaseMapper<UserInfoDto?, UserInfoEntity> {
    override fun map(from: UserInfoDto?): UserInfoEntity = UserInfoEntity(
        userName = from?.userName,
        image = from?.image
    )
}