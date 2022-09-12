package com.mument_android.app.data.mapper.user

import com.mument_android.app.data.dto.UserDto
import com.startup.domain.entity.user.UserEntity
import com.startup.core.base.BaseMapper

class UserMapper: BaseMapper<UserDto, UserEntity> {
    override fun map(from: UserDto): UserEntity =
        UserEntity(from.id, from.name, from.image)
}