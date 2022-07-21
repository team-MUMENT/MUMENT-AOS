package com.mument_android.app.data.mapper.album

import com.mument_android.app.data.dto.MusicDto
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.domain.entity.music.MusicInfoEntity

class MusicInfoMapper: BaseMapper<MusicDto, MusicInfoEntity> {
    override fun map(from: MusicDto): MusicInfoEntity =
        MusicInfoEntity(from.id, from.name, from.image, from.artist)
}