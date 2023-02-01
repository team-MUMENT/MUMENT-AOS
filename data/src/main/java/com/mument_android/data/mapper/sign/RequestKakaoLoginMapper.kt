package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.KakaoDto
import com.mument_android.data.dto.sign.RequestKakaoDto
import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.RequestKakaoData

class RequestKakaoLoginMapper : BaseMapper<RequestKakaoDto, RequestKakaoData> {
    override fun map(from: RequestKakaoDto): RequestKakaoData  = RequestKakaoData(
        provider = from.provider,
        authentication_code = from.authentication_code,
        fcm_token = from.fcm_token
    )
}