package com.mument_android.data.mapper.detail

import com.mument_android.core.base.BaseMapper
import com.mument_android.data.dto.detail.ResponseBlockUserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockUserMapper @Inject constructor() : BaseMapper<ResponseBlockUserDto, Unit> {
    override fun map(from: ResponseBlockUserDto) = run { }
}