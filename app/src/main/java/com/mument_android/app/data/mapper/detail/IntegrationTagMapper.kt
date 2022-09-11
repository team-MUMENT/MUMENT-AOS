package com.mument_android.app.data.mapper.detail

import com.mument_android.app.util.enumtype.EmotionalTag
import com.mument_android.app.util.enumtype.ImpressiveTag
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.domain.entity.TagEntity

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