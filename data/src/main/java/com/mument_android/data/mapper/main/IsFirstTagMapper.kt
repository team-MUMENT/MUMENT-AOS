package com.mument_android.data.mapper.main

import com.mument_android.core.base.BaseMapper
import com.mument_android.core.model.TagEntity
import com.mument_android.core.model.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.data.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsFirstTagMapper @Inject constructor() : BaseMapper<Boolean, TagEntity> {
    override fun map(from: Boolean): TagEntity =
        TagEntity(TAG_IS_FIRST, if (from) R.string.tag_is_first else R.string.tag_has_heard, if (from) 1 else 0)
}