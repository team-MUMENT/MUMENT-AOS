package com.startup.domain.entity.musicdetail

import com.startup.domain.entity.musicdetail.musicdetaildata.Music
import com.startup.domain.entity.musicdetail.musicdetaildata.MyMument

data class MusicDetailEntity(
    val music: Music,
    val myMument: MyMument
)