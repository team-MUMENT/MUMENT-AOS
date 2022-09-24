package com.startup.data.mapper.main

import com.startup.core.model.TagEntity
import com.startup.core.model.TagEntity.Companion.TAG_IS_FIRST
import com.startup.core.base.BaseMapper
import com.startup.data.R

class IsFirstTagMapper: BaseMapper<Boolean, TagEntity> {
    override fun map(from: Boolean): TagEntity =
        TagEntity(TAG_IS_FIRST, if (from) R.string.tag_is_first else R.string.tag_has_heard, if (from) 1 else 0)
}