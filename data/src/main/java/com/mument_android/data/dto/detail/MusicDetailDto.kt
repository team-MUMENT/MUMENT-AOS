package com.mument_android.data.dto.detail

import com.mument_android.data.dto.MumentSummaryDto
import com.mument_android.data.dto.MusicDto

data class MusicDetailDto(
    val music: MusicDto,
    val myMument: MumentSummaryDto?
)