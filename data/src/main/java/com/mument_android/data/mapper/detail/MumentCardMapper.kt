package com.mument_android.data.mapper.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.domain.entity.MumentCard
import com.mument_android.core.base.BaseMapper
import com.mument_android.core_dependent.util.EmotionalTag.Companion.findEmotionalTagEnum
import com.mument_android.core_dependent.util.ImpressiveTag.Companion.findImpressiveTagEnum

class MumentCardMapper: BaseMapper<MusicDetailDto, com.mument_android.domain.entity.MumentCard?> {
    override fun map(from: MusicDetailDto): com.mument_android.domain.entity.MumentCard? {
        val tagIdx = if (from.myMument?.cardTag?.isNotEmpty() == true) from.myMument.cardTag.first() else 100
        return if(from.myMument == null) {
            null
        } else {
            com.mument_android.domain.entity.MumentCard(
                from.myMument.id,
                from.myMument.content ?: "",
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

}