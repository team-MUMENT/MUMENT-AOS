package com.startup.data.mapper.record

import com.startup.data.dto.record.MumentRecordDto
import com.startup.domain.entity.record.MumentRecordEntity
import com.startup.core.base.BaseMapper

class MumentRecordMapper : BaseMapper<MumentRecordEntity, MumentRecordDto> {
    override fun map(from: MumentRecordEntity): MumentRecordDto = MumentRecordDto(
        from.content,
        from.feelingTag,
        from.impressionTag,
        from.isFirst,
        from.isPrivate
    )
}