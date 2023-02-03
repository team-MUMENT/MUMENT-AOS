package com.mument_android.data.dto.home

import com.mument_android.domain.entity.home.Mument

data class Random(
    val mumentList: List<Mument>,
    val title: String
)
