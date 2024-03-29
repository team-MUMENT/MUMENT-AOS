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
        val mumentSortType: SortTypeEnum = SortTypeEnum.SORT_LIKE_COUNT,
        val startNav: String = ""
    ) : ViewState

    sealed class MusicDetailEvent : Event {
        object ClickSortByLikeCount : MusicDetailEvent()
        object ClickSortByLatest : MusicDetailEvent()
        object OnClickBackButton : MusicDetailEvent()
        data class ReceiveRequestMusicInfo(val music: MusicInfoEntity) : MusicDetailEvent()
        data class ReceiveStartNav(val startNav: String) : MusicDetailEvent()
        data class CheckLikeMument(val mumentId: String, val resultCallback: (Boolean) -> Unit) : MusicDetailEvent()
        data class UnCheckLikeMument(val mumentId: String, val resultCallback: (Boolean) -> Unit) : MusicDetailEvent()
        data class CheckLikeItemMument(val mumentId: String, val resultCallback: (Boolean) -> Unit) : MusicDetailEvent()
        data class UnCheckLikeItemMument(val mumentId: String, val resultCallback: (Boolean) -> Unit) : MusicDetailEvent()
    }

    sealed class MusicDetailEffect : SideEffect {
        data class PopBackStack(val startNav: String) : MusicDetailEffect()
        data class ShowToast(val msg: String) : MusicDetailEffect()
        object CompleteLikeMument : MusicDetailEffect()
    }
}