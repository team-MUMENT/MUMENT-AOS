package com.mument_android.data.mapper.app

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.LimitUserDto
import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.domain.entity.LimitUserEntity
import com.mument_android.domain.entity.mypage.BlockUserEntity

class LimitUserMapper : BaseMapper<LimitUserDto?, LimitUserEntity> {
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