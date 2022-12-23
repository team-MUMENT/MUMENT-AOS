package com.mument_android.detail.music

import com.mument_android.core.util.Event
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.ViewState
import com.mument_android.detail.util.SortTypeEnum
import com.mument_android.domain.entity.detail.MumentSummaryEntity
import com.mument_android.domain.entity.music.MusicInfoEntity

sealed class MusicDetailContract {
    data class MusicDetailViewState(
        override val hasError: Boolean = false,
        override val onNetwork: Boolean = false,
        val requestMusicId: String = "",
        val musicInfo: MusicInfoEntity? = null,
        val myMumentInfo: MumentSummaryEntity? = null,
        val mumentList: List<MumentSummaryEntity> = emptyList(),
        val mumentSortType: SortTypeEnum = SortTypeEnum.SORT_LIKE_COUNT
    ): ViewState

    sealed class MusicDetailEvent: Event {
        data class ReceiveRequestMusicId(val musicId: String): MusicDetailEvent()
    }

    sealed class MusicDetailEffect: SideEffect {

    }
}