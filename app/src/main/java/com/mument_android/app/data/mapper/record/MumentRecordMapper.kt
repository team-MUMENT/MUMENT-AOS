package com.mument_android.app.data.mapper.record

import com.mument_android.app.data.dto.record.MumentRecordDto
import com.mument_android.app.domain.entity.record.MumentRecordEntity
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