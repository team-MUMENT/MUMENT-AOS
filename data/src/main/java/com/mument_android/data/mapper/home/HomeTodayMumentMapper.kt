package com.mument_android.data.mapper.home

import com.mument_android.core.base.BaseMapper
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.util.EmotionalTag
import com.mument_android.core_dependent.util.ImpressiveTag
import com.mument_android.data.dto.home.Today
import com.mument_android.data.local.todaymument.TodayMumentEntity
import com.mument_android.domain.entity.home.TodayMument
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeTodayMumentMapper @Inject constructor() : BaseMapper<TodayMumentEntity, TodayMument> {
    override fun map(from: TodayMumentEntity): TodayMument = TodayMument(
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

    fun mapObject(from: Today): TodayMument = TodayMument(
        cardTag = from.todayMument.cardTag.map { tag ->
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
        content = from.todayMument.content,
        createdAt = from.todayMument.createdAt,
        date = from.todayMument.date,
        displayDate = from.todayMument.displayDate,
        feelingTag = from.todayMument.feelingTag,
        impressionTag = from.todayMument.impressionTag,
        isFirst = from.todayMument.isFirst,
        mumentId = from.todayMument.mumentId.toString(),
        musicId = from.todayMument.music._id,
        musicArtist = from.todayMument.music.artist,
        musicImage = from.todayMument.music.image,
        musicName = from.todayMument.music.name,
        todayDate = from.todayDate,
        userId = from.todayMument.user._id.toString(),
        userImage = from.todayMument.user.image,
        userName = from.todayMument.user.name
    )


    fun mapReverse(from: TodayMument): TodayMumentEntity = TodayMumentEntity(
        from.userId,
        from.userName,
        from.userImage ?: "",
        from.cardTag.map {
            it.tagIdx
        },
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