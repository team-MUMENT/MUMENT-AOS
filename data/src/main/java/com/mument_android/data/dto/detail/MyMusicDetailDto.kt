package com.mument_android.data.dto.detail

import com.mument_android.data.dto.MusicDto
import com.mument_android.data.dto.MyMumentSummaryDto

data class MyMusicDetailDto(
    val music: MusicDto,
    val myMument: MyMumentSummaryDto?
)
