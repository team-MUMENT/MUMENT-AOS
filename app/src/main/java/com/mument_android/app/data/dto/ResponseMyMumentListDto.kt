package com.mument_android.app.data.dto

import com.mument_android.app.domain.entity.MumentCard

data class ResponseMyMumentListDto(
    val user: UserDto,
    val muments: List<MumentCard>
)