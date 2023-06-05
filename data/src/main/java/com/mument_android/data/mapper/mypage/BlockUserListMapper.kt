package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.domain.entity.mypage.BlockUserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockUserListMapper @Inject constructor() : BaseMapper<List<BlockUserDto>, List<BlockUserEntity>> {
    override fun map(from: List<BlockUserDto>): List<BlockUserEntity> =
        from.map { element ->
            BlockUserEntity(
                element.id,
                element.profileId,
                element.image
            )
        }
}