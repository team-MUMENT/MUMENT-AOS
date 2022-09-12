package com.mument_android.app.data.mapper.main

import com.startup.core_dependent.util.ImpressiveTag
import com.startup.core.base.BaseMapper
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IMPRESSIVE

class ImpressiveTagMapper: BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity =
        TagEntity(TAG_IMPRESSIVE, ImpressiveTag.findImpressiveStringTag(from), from)
}