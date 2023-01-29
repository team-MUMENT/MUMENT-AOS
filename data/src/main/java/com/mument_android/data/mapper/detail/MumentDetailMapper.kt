package com.mument_android.data.mapper.detail

import com.mument_android.data.dto.detail.MumentDetailDto
import com.mument_android.data.mapper.album.MusicInfoMapper
import com.mument_android.data.mapper.main.EmotionalTagMapper
import com.mument_android.data.mapper.main.ImpressiveTagMapper
import com.mument_android.data.mapper.main.IsFirstTagMapper
import com.mument_android.data.mapper.user.UserMapper
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.core.base.BaseMapper
import com.mument_android.core.model.TagEntity
import com.mument_android.domain.entity.detail.MumentEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.user.UserEntity
import javax.inject.Inject

class MumentDetailMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val impressiveTagMapper: ImpressiveTagMapper,
    private val emotionalTagMapper: EmotionalTagMapper,
    private val isFirstTagMapper: IsFirstTagMapper
): BaseMapper<MumentDetailDto, MumentDetailEntity> {
    override fun map(from: MumentDetailDto): MumentDetailEntity {
        return MumentDetailEntity(
            mument = MumentEntity(
                writerInfo = userMapper.map(from.user),
                isFirst = isFirstTagMapper.map(from.isFirst),
                impressionTags = from.impressionTag?.map { impressiveTagMapper.map(it) },
                emotionalTags = from.feelingTag?.map { emotionalTagMapper.map(it) },
                content = from.content,
                createdDate = from.createdAt
            ),
            isLiked = from.isLiked,
            likeCount = from.likeCount,
            mumentHistoryCount = from.count
        )
    }
}