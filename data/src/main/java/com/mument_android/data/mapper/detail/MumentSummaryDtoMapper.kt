package com.mument_android.data.mapper.detail

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.MumentSummaryDto
import com.mument_android.data.mapper.album.MusicInfoMapper
import com.mument_android.data.mapper.main.EmotionalTagMapper
import com.mument_android.data.mapper.main.ImpressiveTagMapper
import com.mument_android.data.mapper.main.IsFirstTagMapper
import com.mument_android.data.mapper.user.UserMapper
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.detail.MumentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MumentSummaryDtoMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val musicInfoMapper: MusicInfoMapper,
    private val isFirstTagMapper: IsFirstTagMapper,
    private val impressiveTagMapper: ImpressiveTagMapper,
    private val emotionalTagMapper: EmotionalTagMapper
) : BaseMapper<MumentSummaryDto, MumentDetailEntity> {
    override fun map(from: MumentSummaryDto): MumentDetailEntity {
        return MumentDetailEntity(
            mument = MumentEntity(
                writerInfo = userMapper.map(from.user),
                isFirst = isFirstTagMapper.map(from.isFirst),
                impressionTags = from.impressionTag?.map { impressiveTagMapper.map(it) },
                emotionalTags = from.feelingTag?.map { emotionalTagMapper.map(it) },
                content = from.content,
                createdDate = from.createdAt,
                isPrivate = from.isPrivate
            ),
            isLiked = from.isLiked,
            likeCount = from.likeCount,
            mumentHistoryCount = 0
        )
    }
}