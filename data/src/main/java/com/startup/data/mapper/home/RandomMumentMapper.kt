package com.startup.data.mapper.home

import com.startup.data.dto.home.RandomMumentDto
import com.startup.domain.entity.home.RandomMumentEntity
import com.startup.core.base.BaseMapper

class RandomMumentMapper : BaseMapper<RandomMumentDto, RandomMumentEntity> {
    override fun map(from: RandomMumentDto): RandomMumentEntity =
        RandomMumentEntity(from.mumentList, from.title)
}