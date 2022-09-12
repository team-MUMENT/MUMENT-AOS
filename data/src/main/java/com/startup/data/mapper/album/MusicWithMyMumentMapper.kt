package com.startup.data.mapper.album

import com.startup.data.dto.detail.MusicDetailDto
import com.startup.data.mapper.detail.MumentSummaryMapper
import com.startup.domain.entity.detail.MusicWithMyMumentEntity
import com.startup.core.base.BaseMapper
import com.startup.data.mapper.album.MusicInfoMapper
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