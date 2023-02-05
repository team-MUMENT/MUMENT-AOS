package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.RequestSetProfileDto
import com.mument_android.domain.entity.sign.SetProfileData

class RequestSetProfileMapper : BaseMapper<SetProfileData, RequestSetProfileDto>{
    override fun map(from: SetProfileData): RequestSetProfileDto = RequestSetProfileDto(
        image = from.image,
        profileId = from.profileId
    )
}