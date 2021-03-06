package com.mument_android.app.data.mapper.detail

import com.mument_android.app.data.dto.MumentSummaryDto
import com.mument_android.app.data.dto.MusicDto
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.data.mapper.album.MusicInfoMapper
import com.mument_android.app.data.mapper.main.EmotionalTagMapper
import com.mument_android.app.data.mapper.main.ImpressiveTagMapper
import com.mument_android.app.data.mapper.main.IsFirstTagMapper
import com.mument_android.app.data.mapper.user.UserMapper
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
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