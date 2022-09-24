package com.startup.data.mapper.main

import com.startup.core.model.TagEntity
import com.startup.core.model.TagEntity.Companion.TAG_EMOTIONAL
import com.startup.core.base.BaseMapper
import com.startup.core_dependent.util.EmotionalTag.Companion.findEmotionalStringTag

class EmotionalTagMapper: BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity =
        TagEntity(TAG_EMOTIONAL, findEmotionalStringTag(from), from)
}