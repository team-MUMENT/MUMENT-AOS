package com.mument_android.data.mapper.album

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.data.mapper.detail.MumentSummaryMapper
import com.mument_android.domain.entity.detail.MusicWithMyMumentEntity
import com.mument_android.core.base.BaseMapper
import javax.inject.Inject

class MusicWithMyMumentMapper @Inject constructor(
    private val musicInfoMapper: MusicInfoMapper,
    private val mumentSummaryMapper: MumentSummaryMapper
): BaseMapper<MusicDetailDto, MusicWithMyMumentEntity> {
    override fun map(from: MusicDetailDto): MusicWithMyMumentEntity {
        return MusicWithMyMumentEntity(
            musicInfoMapper.map(from.music),
            if (from.myMument == null) null else mumentSummaryMapper.map(from.myMument)
        )
    }
}