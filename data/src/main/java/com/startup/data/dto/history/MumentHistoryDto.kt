package com.startup.data.dto.history

import com.startup.domain.entity.history.MumentHistory
import com.startup.domain.entity.history.MusicX

data class MumentHistoryDto(
    val mumentHistory: List<MumentHistory>?,
    val music: MusicX
)