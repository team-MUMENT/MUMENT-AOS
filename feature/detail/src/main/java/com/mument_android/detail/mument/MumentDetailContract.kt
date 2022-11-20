package com.mument_android.detail.mument

import com.mument_android.core.model.TagEntity
import com.mument_android.core.util.ViewState
import com.mument_android.detail.R
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.user.UserEntity

class MumentDetailContract {
    data class MumentDetailState(
        val writer: UserEntity? = null,
        val musicInfo: MusicInfoEntity? = null,
        val isFirst: TagEntity = TagEntity(TagEntity.TAG_IS_FIRST, R.string.tag_is_first , 1),
        val tags: List<TagEntity>? = null,
        val content: String? = null,
        val createdDate: String = "",
        val isLiked: Boolean = false,
        val mumentHistoryCount: Int = 0,
        val likeCount: Int = 0,
        val hasError: Boolean = false,
        val onNetwork: Boolean = false
    ): ViewState
}