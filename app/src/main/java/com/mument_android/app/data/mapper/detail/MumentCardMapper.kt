package com.mument_android.app.data.mapper.detail

import com.mument_android.app.data.dto.detail.MusicDetailDto
import com.mument_android.app.data.enumtype.EmotionalTag.Companion.findEmotionalTagEnum
import com.mument_android.app.data.enumtype.ImpressiveTag.Companion.findImpressiveTagEnum
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.domain.entity.MumentCard

class MumentCardMapper: BaseMapper<MusicDetailDto, MumentCard> {
    override fun map(from: MusicDetailDto): MumentCard {
        val tagIdx = if (from.myMument.cardTag.isNotEmpty()) from.myMument.cardTag.first() else 100
        return MumentCard(
            from.myMument.id,
            from.myMument.content,
            from.myMument.createdAt,
            from.music.id,
            from.music.image,
            from.music.name,
            from.music.artist,
            from.myMument.user.id,
            from.myMument.user.image,
            from.myMument.user.name,
            from.myMument.likeCount,
            from.myMument.isLiked,
            from.myMument.isPrivate,
            from.myMument.isFirst,
            if (tagIdx < 200) findImpressiveTagEnum(tagIdx).tag else findEmotionalTagEnum(tagIdx).tag,
            if (tagIdx < 200) findImpressiveTagEnum(tagIdx).tag else findEmotionalTagEnum(tagIdx).tag
        )
    }

}