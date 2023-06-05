package com.mument_android.data.mapper.mypage

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.mypage.UnregisterReasonDto
import com.mument_android.domain.entity.mypage.UnregisterReasonEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnregisterReasonMapper @Inject constructor() : BaseMapper<UnregisterReasonDto, UnregisterReasonEntity> {
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