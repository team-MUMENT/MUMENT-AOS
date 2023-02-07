package com.mument_android.data.dto.sign

data class RequestKakaoDto(
    val provider : String,
    val authentication_code : String,
    val fcm_token : String
)
