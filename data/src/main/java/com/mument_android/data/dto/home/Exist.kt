package com.mument_android.data.dto.home

data class Exist(
    val exist: Boolean,
    val officialIdList: List<Int>
)//{"status":400,"success":false,"message":"이미 차단한 유저입니다"}
//{"status":201,"success":true, "message":"유저 차단하기 성공","data":{"exist":142}}