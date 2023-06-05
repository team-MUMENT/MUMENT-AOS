package com.mument_android.data.mapper.record

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.record.MumentIsFirstDto
import com.mument_android.domain.entity.record.RecordIsFirstEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecordMapper @Inject constructor() : BaseMapper<MumentIsFirstDto, RecordIsFirstEntity> {
    override fun map(from: MumentIsFirstDto): RecordIsFirstEntity =
        RecordIsFirstEntity(from.isFirst, from.FirstAvaliable)
}
