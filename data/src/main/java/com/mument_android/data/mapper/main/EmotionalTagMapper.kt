package com.mument_android.data.mapper.main

import com.mument_android.core.model.TagEntity
import com.mument_android.core.model.TagEntity.Companion.TAG_EMOTIONAL
import com.mument_android.core.base.BaseMapper
import com.mument_android.core_dependent.util.EmotionalTag.Companion.findEmotionalStringTag

class EmotionalTagMapper: BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity =
        TagEntity(TAG_EMOTIONAL, findEmotionalStringTag(from), from)
}