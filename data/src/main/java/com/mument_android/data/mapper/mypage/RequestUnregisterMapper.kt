package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.RequestUnregisterDto
import com.mument_android.domain.entity.mypage.RequestUnregisterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestUnregisterMapper @Inject constructor() :
    BaseMapper<RequestUnregisterEntity, RequestUnregisterDto> {
    override fun map(from: RequestUnregisterEntity): RequestUnregisterDto = RequestUnregisterDto(
        socialToken = from.socialToken
    )
}