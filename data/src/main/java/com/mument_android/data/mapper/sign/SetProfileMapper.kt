package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.domain.entity.sign.SetProfileEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetProfileMapper @Inject constructor() : BaseMapper<SetProfileDto, SetProfileEntity> {
    override fun map(from: SetProfileDto): SetProfileEntity = SetProfileEntity(
        id = from.id,
        accessToken = from.accessToken,
        refreshToken = from.refreshToken,
        userName = from.userName,
        image = from.image
    )
}
