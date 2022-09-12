package com.startup.data.dto.detail

import com.startup.data.dto.MumentSummaryDto
import com.startup.data.dto.MusicDto

data class MusicDetailDto(
    val music: MusicDto,
    val myMument: MumentSummaryDto?
)