package com.mument_android.data.mapper.user

import com.mument_android.data.dto.UserDto
import com.mument_android.domain.entity.user.UserEntity
import com.mument_android.core.base.BaseMapper

class UserMapper: BaseMapper<UserDto, UserEntity> {
    override fun map(from: UserDto): UserEntity =
        UserEntity(from.id, from.name, from.image)
}