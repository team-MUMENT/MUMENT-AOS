package com.mument_android.app.data.mapper.main

import com.startup.domain.entity.TagEntity
import com.startup.domain.entity.TagEntity.Companion.TAG_IMPRESSIVE
import com.startup.core.base.BaseMapper
import com.startup.core_dependent.util.ImpressiveTag

class ImpressiveTagMapper: BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity =
        TagEntity(TAG_IMPRESSIVE, ImpressiveTag.findImpressiveStringTag(from), from)
}