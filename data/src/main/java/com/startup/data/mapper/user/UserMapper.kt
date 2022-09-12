package com.startup.data.mapper.user

import com.startup.data.dto.UserDto
import com.startup.domain.entity.user.UserEntity
import com.startup.core.base.BaseMapper

class UserMapper: BaseMapper<UserDto, UserEntity> {
    override fun map(from: UserDto): UserEntity =
        UserEntity(from.id, from.name, from.image)
}