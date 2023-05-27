package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.RequestUnregisterReasonDto
import com.mument_android.domain.entity.mypage.RequestUnregisterReasonEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestUnregisterReasonMapper @Inject constructor() :
    BaseMapper<RequestUnregisterReasonEntity, RequestUnregisterReasonDto> {
    override fun map(from: RequestUnregisterReasonEntity): RequestUnregisterReasonDto =
        RequestUnregisterReasonDto(
            from.leaveCategoryId,
            from.reasonEtc
        )


}