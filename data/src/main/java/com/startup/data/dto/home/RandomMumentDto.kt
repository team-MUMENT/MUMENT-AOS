package com.startup.data.dto.home

import com.startup.domain.entity.home.Mument

data class RandomMumentDto(
    val mumentList: List<Mument>,
    val title: String
)