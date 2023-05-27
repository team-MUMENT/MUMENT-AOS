package com.mument_android.data.mapper.album

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.detail.MyMusicDetailDto
import com.mument_android.data.mapper.detail.MyMumentSummaryMapper
import com.mument_android.domain.entity.detail.MusicWithMyMumentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicWithMyMumentMapper @Inject constructor(
    private val musicInfoMapper: MusicInfoMapper,
    private val mumentSummaryMapper: MyMumentSummaryMapper
) : BaseMapper<MyMusicDetailDto, MusicWithMyMumentEntity> {
    override fun map(from: MyMusicDetailDto): MusicWithMyMumentEntity {
        return MusicWithMyMumentEntity(
            musicInfoMapper.map(from.music),
            if (from.myMument == null) null else mumentSummaryMapper.map(from.myMument)
        )
    }
}