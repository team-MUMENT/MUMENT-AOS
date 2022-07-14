package com.mument_android.app.data.mapper.main

import com.mument_android.app.data.enumtype.EmotionalTag.Companion.findEmotionalStringTag
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_EMOTIONAL

class EmotionalTagMapper: BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity =
        TagEntity(TAG_EMOTIONAL, findEmotionalStringTag(from), from)
}