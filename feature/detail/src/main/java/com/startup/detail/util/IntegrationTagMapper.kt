package com.startup.detail.util

import com.startup.core.model.TagEntity
import com.startup.core.base.BaseMapper
import com.startup.core_dependent.util.EmotionalTag
import com.startup.core_dependent.util.ImpressiveTag

/**
 * 감정태그, 인상깊은 부분 태그 종류에 상관없이 TagEntity로 반환
 */
class IntegrationTagMapper: BaseMapper<Int, TagEntity> {
    override fun map(from: Int): TagEntity {
        val type = if (from < 200) TagEntity.TAG_IMPRESSIVE else TagEntity.TAG_EMOTIONAL
        val tag = if (from < 200) ImpressiveTag.findImpressiveStringTag(from) else EmotionalTag.findEmotionalStringTag(from)
        return TagEntity(type, tag, from)
    }
}