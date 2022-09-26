package com.mument_android.data.mapper.detail

import com.mument_android.data.dto.MumentSummaryDto
import com.mument_android.data.mapper.user.UserMapper
import com.mument_android.domain.entity.detail.MumentSummaryEntity
import com.mument_android.core.base.BaseMapper
import javax.inject.Inject

class MumentSummaryMapper @Inject constructor(
    private val userMapper: UserMapper
): BaseMapper<MumentSummaryDto, MumentSummaryEntity> {
    override fun map(from: MumentSummaryDto): MumentSummaryEntity {
        return MumentSummaryEntity(
            from.id,
            from.music.id,
            userMapper.map(from.user),
            from.isFirst,
            from.impressionTag ?: listOf(),
            from.feelingTag ?: listOf(),
            from.content,
            from.isPrivate,
            from.likeCount,
            from.isDeleted,
            from.date,
            from.cardTag,
            from.isLiked
        )
    }
}