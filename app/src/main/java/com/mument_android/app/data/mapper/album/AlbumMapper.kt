package com.mument_android.app.data.mapper.album

import com.mument_android.app.data.dto.MusicDto
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.domain.entity.album.AlbumEntity

class AlbumMapper: BaseMapper<MusicDto, AlbumEntity> {
    override fun map(from: MusicDto): AlbumEntity =
        AlbumEntity(from.id, from.name, from.image, from.artist)
}