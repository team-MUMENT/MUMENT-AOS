package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.KakaoDto
import com.mument_android.domain.entity.sign.KakaoEntity

class KakaoLoginMapper : BaseMapper<KakaoDto, KakaoEntity> {
    override fun map(from: KakaoDto): KakaoEntity  = KakaoEntity(
        type = from.type,
        accessToken = from.accessToken,
        refreshToken = from.refreshToken
    )
}