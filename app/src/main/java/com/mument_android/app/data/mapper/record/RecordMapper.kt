package com.mument_android.app.data.mapper.record

import com.mument_android.app.data.dto.UserDto
import com.mument_android.app.data.dto.record.MumentIsFirstDto
import com.mument_android.app.data.mapper.BaseMapper
import com.mument_android.app.domain.entity.record.RecordIsFirstEntity
import com.mument_android.app.domain.entity.user.UserEntity

class RecordMapper : BaseMapper<MumentIsFirstDto, RecordIsFirstEntity> {
    override fun map(from:MumentIsFirstDto):RecordIsFirstEntity =
        RecordIsFirstEntity(from.isFirst,from.FirstAvaliable)
}
