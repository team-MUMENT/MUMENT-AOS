package com.startup.data.mapper.main

import com.startup.core.model.TagEntity
import com.startup.core.model.TagEntity.Companion.TAG_IMPRESSIVE
import com.startup.core.base.BaseMapper
import com.startup.core_dependent.util.ImpressiveTag

class ImpressiveTagMapper: BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity =
        TagEntity(TAG_IMPRESSIVE, ImpressiveTag.findImpressiveStringTag(from), from)
}