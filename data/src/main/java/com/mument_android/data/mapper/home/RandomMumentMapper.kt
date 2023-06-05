package com.mument_android.data.mapper.home

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.domain.entity.home.RandomMumentEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RandomMumentMapper @Inject constructor() : BaseMapper<RandomMumentDto, RandomMumentEntity> {
    override fun map(from: RandomMumentDto): RandomMumentEntity =
        RandomMumentEntity(from.data.mumentList, from.data.title)
}