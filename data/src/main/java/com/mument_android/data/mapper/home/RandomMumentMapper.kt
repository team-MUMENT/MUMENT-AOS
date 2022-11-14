package com.mument_android.data.mapper.home

import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.domain.entity.home.RandomMumentEntity
import com.mument_android.core.base.BaseMapper

class RandomMumentMapper : BaseMapper<RandomMumentDto, RandomMumentEntity> {
    override fun map(from: RandomMumentDto): RandomMumentEntity =
        RandomMumentEntity(from.mumentList, from.title)
}