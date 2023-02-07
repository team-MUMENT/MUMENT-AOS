package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.UnregisterReasonDto
import com.mument_android.domain.entity.mypage.UnregisterEntity
import com.mument_android.domain.entity.mypage.UnregisterReasonEntity
import java.util.*

class UnregisterReasonMapper : BaseMapper<UnregisterReasonDto, UnregisterReasonEntity> {
    override fun map(from: UnregisterReasonDto): UnregisterReasonEntity = UnregisterReasonEntity(
        from.id,
        from.userId,
        from.profileId,
        from.leaveCategoryId,
        from.leaveCategoryName,
        from.reasonEtc,
        from.createdAt
    )
}