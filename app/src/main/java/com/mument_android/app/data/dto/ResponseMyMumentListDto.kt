package com.mument_android.app.data.dto

data class ResponseMyMumentListDto(
    val user: UserDto,
    val muments: List<MumentCard>
)