package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.KakaoDto
import com.mument_android.data.dto.sign.RequestKakaoDto
import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.RequestKakaoData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoLoginMapper @Inject constructor() : BaseMapper<KakaoDto, KakaoEntity> {
    override fun map(from: KakaoDto): KakaoEntity = KakaoEntity(
        from._id,
        from.type,
        from.accessToken,
        from.refreshToken
    )

    fun requestMap(from: RequestKakaoData): RequestKakaoDto = RequestKakaoDto(
        from.provider,
        from.authentication_code,
        from.fcm_token
    )
}