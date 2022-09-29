package com.mument_android.data.dto

import com.mument_android.domain.entity.locker.TestMumentCard

data class MyMumentListDto(
    val user: UserDto,
    //val muments: List<MumentCard>
    val muments : List<TestMumentCard>
)