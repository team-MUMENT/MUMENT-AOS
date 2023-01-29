package com.mument_android.data.mapper.home

import com.mument_android.core.base.BaseMapper
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.util.EmotionalTag
import com.mument_android.core_dependent.util.ImpressiveTag
import com.mument_android.data.local.todaymument.TodayMumentEntity
import com.mument_android.domain.entity.home.TodayMument

class HomeTodayMumentMapper : BaseMapper<TodayMumentEntity, TodayMument> {
    override fun map(from: TodayMumentEntity): TodayMument = TodayMument(
        _id = from._id,
        cardTag = from.cardTag.map { tag ->
            if (tag < 200) TagEntity(
                TagEntity.TAG_IMPRESSIVE,
                ImpressiveTag.findImpressiveStringTag(tag),
                tag
            )
            else TagEntity(
                TagEntity.TAG_EMOTIONAL,
                EmotionalTag.findEmotionalStringTag(tag),
                tag
            )
        },
        content = from.content,
        createdAt = from.createdAt,
        date = from.date,
        displayDate = from.displayDate,
        feelingTag = from.feelingTag,
        impressionTag = from.impressionTag,
        isFirst = from.isFirst,
        mumentId = from.mumentId,
        musicId = from.musicId,
        musicArtist = from.musicArtist,
        musicImage = from.musicImage,
        musicName = from.musicName,
        todayDate = from.todayDate,
        userId = from.userId,
        userImage = from.userImage,
        userName = from.userName
    )

    fun mapReverse(from: TodayMument): TodayMumentEntity = TodayMumentEntity(
        from.userId,
        from.userName,
        from.userImage,
        from.cardTag.map {
            it.tagIdx
        },
        from._id,
        from.content,
        from.createdAt,
        from.feelingTag,
        from.impressionTag,
        from.displayDate,
        from.isFirst,
        from.date,
        from.mumentId,
        from.musicId,
        from.musicName,
        from.musicArtist,
        from.musicImage,
        from.todayDate,
    )
}