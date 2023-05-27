package com.mument_android.data.mapper.detail

import com.mument_android.core.base.BaseMapper
import com.mument_android.core_dependent.util.EmotionalTag.Companion.findEmotionalTagEnum
import com.mument_android.core_dependent.util.ImpressiveTag.Companion.findImpressiveTagEnum
import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.domain.entity.MumentCard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MumentCardMapper @Inject constructor() : BaseMapper<MusicDetailDto, MumentCard?> {
    override fun map(from: MusicDetailDto): MumentCard? {
        val tagIdx = if (from.myMument?.cardTag?.isNotEmpty() == true) from.myMument.cardTag.first() else 100
        return if (from.myMument == null) {
            null
        } else {
            MumentCard(
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