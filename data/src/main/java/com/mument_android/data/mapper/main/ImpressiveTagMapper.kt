package com.mument_android.data.mapper.main

import com.mument_android.core.base.BaseMapper
import com.mument_android.core.model.TagEntity
import com.mument_android.core.model.TagEntity.Companion.TAG_IMPRESSIVE
import com.mument_android.core_dependent.util.ImpressiveTag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImpressiveTagMapper @Inject constructor() : BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity =
        TagEntity(TAG_IMPRESSIVE, ImpressiveTag.findImpressiveStringTag(from), from)
}