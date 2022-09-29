package com.mument_android.data.dto.history

import com.mument_android.domain.entity.history.MumentHistory
import com.mument_android.domain.entity.history.MusicX

data class MumentHistoryDto(
    val mumentHistory: List<MumentHistory>?,
    val music: MusicX
)