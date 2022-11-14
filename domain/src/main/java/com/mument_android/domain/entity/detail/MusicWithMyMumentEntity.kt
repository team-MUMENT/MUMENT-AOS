package com.mument_android.domain.entity.detail

import com.mument_android.domain.entity.music.MusicInfoEntity

data class MusicWithMyMumentEntity(
    val music: MusicInfoEntity,
    val myMument: MumentSummaryEntity?
)