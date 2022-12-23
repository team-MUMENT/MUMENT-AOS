package com.mument_android.detail.music

import com.mument_android.core.util.ViewState
import com.mument_android.domain.entity.music.MusicInfoEntity

sealed class MusicDetailContract {
    data class MusicDetailViewState(
        override val hasError: Boolean,
        override val onNetwork: Boolean,
        val musicInfo: MusicInfoEntity
    ): ViewState
}