package com.mument_android.domain.entity.musicdetail

import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.domain.entity.musicdetail.musicdetaildata.MyMument

data class MusicDetailEntity(
    val music: Music,
    val myMument: MyMument
)