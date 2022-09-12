package com.mument_android.app.data.dto

import com.startup.domain.entity.locker.TestMumentCard

data class MyMumentListDto(
    val user: UserDto,
    //val muments: List<MumentCard>
    val muments : List<TestMumentCard>
)