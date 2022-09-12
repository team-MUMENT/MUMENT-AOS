package com.startup.data.mapper.detail

import com.startup.data.dto.MumentSummaryDto
import com.startup.data.dto.MusicDto
import com.startup.data.mapper.album.MusicInfoMapper
import com.startup.data.mapper.main.EmotionalTagMapper
import com.startup.data.mapper.main.ImpressiveTagMapper
import com.startup.data.mapper.main.IsFirstTagMapper
import com.startup.data.mapper.user.UserMapper
import com.startup.domain.entity.detail.MumentDetailEntity
import com.startup.core.base.BaseMapper
import javax.inject.Inject

class MumentSummaryDtoMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val musicInfoMapper: MusicInfoMapper,
    private val isFirstTagMapper: IsFirstTagMapper,
    private val impressiveTagMapper: ImpressiveTagMapper,
    private val emotionalTagMapper: EmotionalTagMapper
): BaseMapper<MumentSummaryDto, MumentDetailEntity> {
    override fun map(from: MumentSummaryDto): MumentDetailEntity {
        val musicDto = MusicDto(from.music.id, "", "", "")
        return MumentDetailEntity(
            userMapper.map(from.user),
            musicInfoMapper.map(musicDto),
            isFirstTagMapper.map(from.isFirst),
            from.impressionTag?.map { impressiveTagMapper.map(it) },
            from.feelingTag?.map { emotionalTagMapper.map(it) },
            from.content,
            from.createdAt,
            from.isLiked,
            0,
            from.likeCount
        )
    }
}