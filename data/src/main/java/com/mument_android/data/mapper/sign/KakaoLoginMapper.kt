package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.KakaoDto
import com.mument_android.data.dto.sign.RequestKakaoDto
import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.RequestKakaoData

class KakaoLoginMapper : BaseMapper<KakaoDto?, KakaoEntity?> {
    override fun map(from: KakaoDto?): KakaoEntity?  = KakaoEntity(
        from?.type,
        from?.accessToken,
        from?.refreshToken
    )

    fun requestMap(from: RequestKakaoData) : RequestKakaoDto = RequestKakaoDto(
        from.provider,
        from.authentication_code,
        from.fcm_token
    )

}