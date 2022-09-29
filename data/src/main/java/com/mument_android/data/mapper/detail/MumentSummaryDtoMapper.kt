package com.mument_android.data.mapper.detail

import com.mument_android.data.dto.MumentSummaryDto
import com.mument_android.data.dto.MusicDto
import com.mument_android.data.mapper.album.MusicInfoMapper
import com.mument_android.data.mapper.main.EmotionalTagMapper
import com.mument_android.data.mapper.main.ImpressiveTagMapper
import com.mument_android.data.mapper.main.IsFirstTagMapper
import com.mument_android.data.mapper.user.UserMapper
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.core.base.BaseMapper
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