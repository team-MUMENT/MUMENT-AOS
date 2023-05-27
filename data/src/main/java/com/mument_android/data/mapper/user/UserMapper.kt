package com.mument_android.data.mapper.user

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.UserDto
import com.mument_android.domain.entity.user.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserMapper @Inject constructor() : BaseMapper<UserDto, UserEntity> {
    override fun map(from: UserDto): UserEntity =
        UserEntity(from.id, from.name, from.image)
}