package com.mument_android.app.domain.entity.home

import com.mument_android.app.data.dto.home.Mument

data class RandomMumentEntity(
    val mumentList: List<Mument>,
    val title: String
)