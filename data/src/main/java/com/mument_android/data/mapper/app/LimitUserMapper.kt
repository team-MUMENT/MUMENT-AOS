package com.mument_android.data.mapper.app

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.LimitUserDto
import com.mument_android.domain.entity.LimitUserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LimitUserMapper @Inject constructor() : BaseMapper<LimitUserDto?, LimitUserEntity> {
    override fun map(from: LimitUserDto?): LimitUserEntity =
        LimitUserEntity(
            from?.endDate,
            from?.musicArtist,
            from?.musicTitle,
            from?.period,
            from?.reason,
            from?.restricted
        )
}