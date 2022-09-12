package com.mument_android.app.data.mapper.detail

import com.mument_android.app.data.dto.detail.MumentDetailDto
import com.mument_android.app.data.mapper.album.MusicInfoMapper
import com.mument_android.app.data.mapper.main.EmotionalTagMapper
import com.mument_android.app.data.mapper.main.ImpressiveTagMapper
import com.mument_android.app.data.mapper.main.IsFirstTagMapper
import com.mument_android.app.data.mapper.user.UserMapper
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
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