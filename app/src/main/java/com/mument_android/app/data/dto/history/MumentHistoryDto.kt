package com.mument_android.app.data.dto.history

import com.mument_android.app.domain.entity.history.MumentHistory
import com.mument_android.app.domain.entity.history.MusicX

data class MumentHistoryDto(
    val mumentHistory: List<MumentHistory>?,
    val music: MusicX
)