package com.mument_android.domain.entity.detail

import java.io.Serializable

data class MumentDetailEntity(
    val mument: MumentEntity,
    val likeCount: Int,
    val isLiked: Boolean,
    val mumentHistoryCount: Int
): Serializable {

}