package com.mument_android.domain.entity.detail

// 서버에서 갑자기 이 형태로 요청 부탁해서 급하게 만들었음....
data class MusicReqeust(
    val musicId: Int,
    val musicArtist: String,
    val musicImage: String,
    val musicName: String
)