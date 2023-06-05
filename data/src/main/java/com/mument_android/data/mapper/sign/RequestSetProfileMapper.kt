package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.RequestSetProfileDto
import com.mument_android.domain.entity.sign.SetProfileData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestSetProfileMapper @Inject constructor() : BaseMapper<SetProfileData, RequestSetProfileDto> {
    override fun map(from: SetProfileData): RequestSetProfileDto = RequestSetProfileDto(
        image = from.image,
        userName = from.userName
    )
}