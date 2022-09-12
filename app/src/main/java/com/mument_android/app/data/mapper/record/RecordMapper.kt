package com.mument_android.app.data.mapper.record

import com.mument_android.app.data.dto.record.MumentIsFirstDto
import com.startup.domain.entity.record.RecordIsFirstEntity
import com.startup.core.base.BaseMapper

class RecordMapper : BaseMapper<MumentIsFirstDto, RecordIsFirstEntity> {
    override fun map(from:MumentIsFirstDto):RecordIsFirstEntity =
        RecordIsFirstEntity(from.isFirst,from.FirstAvaliable)
}
