package com.mument_android.domain.entity.sign

data class RequestKakaoData(
    val provider : String,
    val authentication_code : String,
    val fcm_token : String
)
