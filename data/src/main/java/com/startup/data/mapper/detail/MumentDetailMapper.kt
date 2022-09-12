package com.startup.data.mapper.detail

import com.startup.data.dto.detail.MumentDetailDto
import com.startup.data.mapper.album.MusicInfoMapper
import com.startup.data.mapper.main.EmotionalTagMapper
import com.startup.data.mapper.main.ImpressiveTagMapper
import com.startup.data.mapper.main.IsFirstTagMapper
import com.startup.data.mapper.user.UserMapper
import com.startup.domain.entity.detail.MumentDetailEntity
import com.startup.core.base.BaseMapper
import javax.inject.Inject

class MumentDetailMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val musicInfoMapper: MusicInfoMapper,
    private val impressiveTagMapper: ImpressiveTagMapper,
    private val emotionalTagMapper: EmotionalTagMapper,
    private val isFirstTagMapper: IsFirstTagMapper
): BaseMapper<MumentDetailDto, MumentDetailEntity> {
    override fun map(from: MumentDetailDto): MumentDetailEntity {
        return MumentDetailEntity(
            userMapper.map(from.user),
            musicInfoMapper.map(from.music),
            isFirstTagMapper.map(from.isFirst),
            from.impressionTag?.map { impressiveTagMapper.map(it) },
            from.feelingTag?.map { emotionalTagMapper.map(it) },
            from.content,
            from.createdAt,
            from.isLiked,
            from.count,
            from.likeCount
        )
    }
}