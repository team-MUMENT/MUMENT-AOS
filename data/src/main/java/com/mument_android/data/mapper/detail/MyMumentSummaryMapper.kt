package com.mument_android.data.mapper.detail

import com.mument_android.core.base.BaseMapper
import com.mument_android.core.model.TagEntity
import com.mument_android.data.R
import com.mument_android.data.dto.MumentSummaryDto
import com.mument_android.data.dto.MyMumentSummaryDto
import com.mument_android.data.mapper.user.UserMapper
import com.mument_android.domain.entity.detail.MumentSummaryEntity
import javax.inject.Inject

class MyMumentSummaryMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val integrationTagMapper: IntegrationTagMapper
): BaseMapper<MyMumentSummaryDto, MumentSummaryEntity> {
    override fun map(from: MyMumentSummaryDto): MumentSummaryEntity {
        return MumentSummaryEntity(
            from.id,
            from.music.id.toString(),
            userMapper.map(from.user),
            from.isFirst,
            mapTagList(from),
            from.content,
            from.isPrivate,
            from.likeCount,
            from.isDeleted,
            from.createdAt,
            from.date,
            from.isLiked
        )
    }

    private fun mapTagList(mument: MyMumentSummaryDto): List<TagEntity> {
        val cardTags = mutableListOf<TagEntity>()
        val isFirst = if (mument.isFirst) R.string.tag_is_first else R.string.tag_has_heard
        cardTags.add(
            TagEntity(TagEntity.TAG_IS_FIRST, isFirst, if (mument.isFirst) 1 else 0)
        )
        mument.cardTag?.map { tagIdx -> integrationTagMapper.map(tagIdx) }
            ?.let { cardTags.addAll(it) }
        return cardTags
    }
}