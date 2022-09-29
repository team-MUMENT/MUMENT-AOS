package com.mument_android.data.mapper.album

import com.mument_android.data.dto.MusicDto
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.core.base.BaseMapper

class MusicInfoMapper: BaseMapper<MusicDto, MusicInfoEntity> {
    override fun map(from: MusicDto): MusicInfoEntity =
        MusicInfoEntity(from.id, from.name, from.image, from.artist)
}