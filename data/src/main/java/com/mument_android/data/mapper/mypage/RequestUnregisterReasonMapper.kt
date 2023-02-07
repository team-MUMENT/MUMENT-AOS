package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.RequestUnregisterReasonDto
import com.mument_android.domain.entity.mypage.RequestUnregisterReasonEntity

class RequestUnregisterReasonMapper :
    BaseMapper<RequestUnregisterReasonEntity, RequestUnregisterReasonDto> {
    override fun map(from: RequestUnregisterReasonEntity): RequestUnregisterReasonDto =
        RequestUnregisterReasonDto(
            from.leaveCategoryId,
            from.reasonEtc
        )


}