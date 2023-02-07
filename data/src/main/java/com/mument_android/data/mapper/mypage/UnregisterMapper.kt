package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.UnregisterDto
import com.mument_android.domain.entity.mypage.UnregisterEntity

class UnregisterMapper:BaseMapper<UnregisterDto,UnregisterEntity> {
    override fun map(from: UnregisterDto): UnregisterEntity = UnregisterEntity(
        from.id,
        from.profileId,
        from.isDeleted,
        from.updatedAt
    )
}