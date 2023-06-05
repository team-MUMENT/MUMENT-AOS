package com.mument_android.data.mapper.record

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.record.MumentModifyDto
import com.mument_android.domain.entity.record.MumentModifyEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MumentModifyMapper @Inject constructor() : BaseMapper<MumentModifyEntity, MumentModifyDto> {
    override fun map(from: MumentModifyEntity): MumentModifyDto = MumentModifyDto(
        from.content,
        from.feelingTag,
        from.impressionTag,
        from.isFirst,
        from.isPrivate
    )
}