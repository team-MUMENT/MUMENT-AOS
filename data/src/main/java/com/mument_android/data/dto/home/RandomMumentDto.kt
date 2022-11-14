package com.mument_android.data.dto.home

import com.mument_android.domain.entity.home.Mument

data class RandomMumentDto(
    val mumentList: List<Mument>,
    val title: String
)