package com.mument_android.app.data.mapper.home

import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.domain.entity.home.RandomMumentEntity
import com.startup.core.base.BaseMapper

class RandomMumentMapper : BaseMapper<RandomMumentDto, RandomMumentEntity> {
    override fun map(from: RandomMumentDto): RandomMumentEntity =
        RandomMumentEntity(from.mumentList, from.title)
}