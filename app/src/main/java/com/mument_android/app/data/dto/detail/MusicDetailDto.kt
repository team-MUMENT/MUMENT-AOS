package com.mument_android.app.data.dto.detail

import com.mument_android.app.data.dto.MumentSummaryDto
import com.mument_android.app.data.dto.MusicDto

data class MusicDetailDto(
    val music: MusicDto,
    val myMument: MumentSummaryDto?
)