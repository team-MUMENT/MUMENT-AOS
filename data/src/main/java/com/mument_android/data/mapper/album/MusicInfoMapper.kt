package com.mument_android.data.mapper.album

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.MusicDto
import com.mument_android.domain.entity.music.MusicInfoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicInfoMapper @Inject constructor() : BaseMapper<MusicDto, MusicInfoEntity> {
    override fun map(from: MusicDto): MusicInfoEntity =
        MusicInfoEntity(from.id, from.name, from.image, from.artist)
}