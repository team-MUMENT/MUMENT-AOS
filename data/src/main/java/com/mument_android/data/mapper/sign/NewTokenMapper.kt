package com.mument_android.data.mapper.sign

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.sign.NewTokenDto
import com.mument_android.domain.entity.sign.NewTokenEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewTokenMapper @Inject constructor() : BaseMapper<NewTokenDto?, NewTokenEntity?> {
    override fun map(from: NewTokenDto?): NewTokenEntity? = NewTokenEntity(
        from?._id,
        from?.accessToken,
        from?.refreshToken,
        from?.type
    )
}