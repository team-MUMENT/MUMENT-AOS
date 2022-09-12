package com.mument_android.app.data.mapper.album

import com.mument_android.app.data.dto.MusicDto
import com.startup.domain.entity.music.MusicInfoEntity
import com.startup.core.base.BaseMapper

class MusicInfoMapper: BaseMapper<MusicDto, MusicInfoEntity> {
    override fun map(from: MusicDto): MusicInfoEntity =
        MusicInfoEntity(from.id, from.name, from.image, from.artist)
}