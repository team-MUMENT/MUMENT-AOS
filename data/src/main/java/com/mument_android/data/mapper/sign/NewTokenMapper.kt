package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.KakaoDto
import com.mument_android.data.dto.sign.NewTokenDto
import com.mument_android.data.dto.sign.RequestKakaoDto
import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.NewTokenEntity
import com.mument_android.domain.entity.sign.RequestKakaoData

class NewTokenMapper : BaseMapper<NewTokenDto?, NewTokenEntity?> {
    override fun map(from: NewTokenDto?): NewTokenEntity?  = NewTokenEntity(
        from?._id,
        from?.accessToken,
        from?.refreshToken,
        from?.type
    )
}