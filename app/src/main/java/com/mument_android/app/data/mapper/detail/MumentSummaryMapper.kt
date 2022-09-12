package com.mument_android.app.data.mapper.detail

import com.mument_android.app.data.dto.MumentSummaryDto
import com.startup.core.base.BaseMapper
import com.mument_android.app.data.mapper.user.UserMapper
import com.mument_android.app.domain.entity.detail.MumentSummaryEntity
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