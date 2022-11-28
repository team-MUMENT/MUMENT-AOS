package com.mument_android.detail.mument

import com.mument_android.core.model.TagEntity
import com.mument_android.core.util.ViewState
import com.mument_android.detail.R
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.detail.MumentEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.user.UserEntity

class MumentDetailContract {
    data class MumentDetailViewState(
        val mument: MumentEntity? = null,
        val isLiked: Boolean = false,
        val likeCount: Int = 0,
        val mumentHistoryCount: Int = 0,
        val hasError: Boolean = false,
        val onNetwork: Boolean = false
    ): ViewState
}