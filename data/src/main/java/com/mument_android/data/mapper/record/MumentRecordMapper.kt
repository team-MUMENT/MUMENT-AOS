package com.mument_android.data.mapper.record

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.record.MumentRecordDto
import com.mument_android.domain.entity.record.MumentRecordEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MumentRecordMapper @Inject constructor() : BaseMapper<MumentRecordEntity, MumentRecordDto> {
    override fun map(from: MumentRecordEntity): MumentRecordDto = MumentRecordDto(
        from.content,
        from.feelingTag,
        from.impressionTag,
        from.isFirst,
        from.isPrivate,
        from.musicId,
        from.musicArtist,
        from.musicImage,
        from.musicName
    )
}