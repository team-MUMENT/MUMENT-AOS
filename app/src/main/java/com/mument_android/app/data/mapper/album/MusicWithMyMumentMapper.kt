package com.mument_android.app.data.mapper.album

import com.mument_android.app.data.dto.detail.MusicDetailDto
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.data.mapper.detail.MumentSummaryMapper
import com.mument_android.app.domain.entity.detail.MusicWithMyMumentEntity
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