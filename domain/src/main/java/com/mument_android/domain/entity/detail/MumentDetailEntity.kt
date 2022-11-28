package com.mument_android.domain.entity.detail

import com.mument_android.core.model.TagEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.user.UserEntity
import java.io.Serializable

data class MumentDetailEntity(
    val mument: MumentEntity,
    val likeCount: Int,
    val isLiked: Boolean,
    val mumentHistoryCount: Int
): Serializable {

}