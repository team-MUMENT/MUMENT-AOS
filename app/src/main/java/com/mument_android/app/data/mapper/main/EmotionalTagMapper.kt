package com.mument_android.app.data.mapper.main

import com.startup.domain.entity.TagEntity
import com.startup.domain.entity.TagEntity.Companion.TAG_EMOTIONAL
import com.startup.core.base.BaseMapper
import com.startup.core_dependent.util.EmotionalTag.Companion.findEmotionalStringTag

class EmotionalTagMapper: BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity =
        TagEntity(TAG_EMOTIONAL, findEmotionalStringTag(from), from)
}