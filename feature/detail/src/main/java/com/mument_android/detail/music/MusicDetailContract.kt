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
        object ClickSortByLikeCount: MusicDetailEvent()
        object ClickSortByLatest: MusicDetailEvent()
        object OnClickBackButton: MusicDetailEvent()
        data class ReceiveRequestMusicInfo(val music: MusicInfoEntity): MusicDetailEvent()
        data class CheckLikeMument(val mumentId: String): MusicDetailEvent()
        data class UnCheckLikeMument(val mumentId: String): MusicDetailEvent()
        data class CheckLikeItemMument(val mumentId: String): MusicDetailEvent()
        data class UnCheckLikeItemMument(val mumentId: String): MusicDetailEvent()
    }

    sealed class MusicDetailEffect: SideEffect {
        object PopBackStack: MusicDetailEffect()
        data class ShowToast(val msg: String): MusicDetailEffect()
        object CompleteLikeMument: MusicDetailEffect()
    }
}