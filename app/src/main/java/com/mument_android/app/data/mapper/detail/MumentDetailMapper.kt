package com.mument_android.app.data.mapper.detail

import com.mument_android.app.data.dto.ResponseMumentDetailDto
import com.mument_android.app.data.enumtype.EmotionalTag.Companion.findEmotionalTagEnum
import com.mument_android.app.data.enumtype.ImpressiveTag.Companion.findImpressiveTagEnum
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.data.mapper.album.AlbumMapper
import com.mument_android.app.data.mapper.main.EmotionalTagMapper
import com.mument_android.app.data.mapper.main.ImpressiveTagMapper
import com.mument_android.app.data.mapper.main.IsFirstTagMapper
import com.mument_android.app.data.mapper.user.UserMapper
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import javax.inject.Inject

class MumentDetailMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val albumMapper: AlbumMapper,
    private val impressiveTagMapper: ImpressiveTagMapper,
    private val emotionalTagMapper: EmotionalTagMapper,
    private val isFirstTagMapper: IsFirstTagMapper
): BaseMapper<ResponseMumentDetailDto, MumentDetailEntity> {
    override fun map(from: ResponseMumentDetailDto): MumentDetailEntity {
        return MumentDetailEntity(
            userMapper.map(from.user),
            albumMapper.map(from.music),
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