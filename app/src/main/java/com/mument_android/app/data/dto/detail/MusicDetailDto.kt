package com.mument_android.app.data.dto.detail

import com.mument_android.app.data.dto.MusicDto
import com.mument_android.app.data.dto.MumentSummaryDto

data class MusicDetailDto(
    val music: MusicDto,
    val myMument: MumentSummaryDto?
)