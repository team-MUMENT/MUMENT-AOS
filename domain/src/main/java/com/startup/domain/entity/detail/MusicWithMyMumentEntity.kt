package com.startup.domain.entity.detail

import com.startup.domain.entity.music.MusicInfoEntity

data class MusicWithMyMumentEntity(
    val music: MusicInfoEntity,
    val myMument: MumentSummaryEntity?
)