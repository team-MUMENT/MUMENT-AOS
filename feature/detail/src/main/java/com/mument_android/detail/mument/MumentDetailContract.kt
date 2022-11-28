package com.mument_android.detail.mument

import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.UserEvent
import com.mument_android.core.util.ViewState
import com.mument_android.domain.entity.detail.MumentEntity

class MumentDetailContract {
    data class MumentDetailViewState(
        override val hasError: Boolean = false,
        override val onNetwork: Boolean = false,
        val requestMumentId: String = "",
        val isWriter: Boolean = false,
        val mument: MumentEntity? = null,
        val isLikedMument: Boolean = false,
        val likeCount: Int = 0,
        val historyCount: Int = 0,
        val hasWrittenMument: Boolean = false
    ): ViewState

    sealed class MumentDetailEvent: UserEvent {
        data class OnClickAlum(val musicId: String): MumentDetailEvent()
        data class OnClickHistory (val musicId: String): MumentDetailEvent()
        data class OnClickEditMument (val mument: String): MumentDetailEvent()
        object OnClickBackIcon: MumentDetailEvent()
        object OnClickLikeMument: MumentDetailEvent()
        object OnClickUnLikeMument: MumentDetailEvent()
        object OnClickDeleteMument: MumentDetailEvent()
    }

    sealed class MumentDetailSideEffect: SideEffect {
        object PopBackStack: MumentDetailSideEffect()
        data class NavToMusicDetail(val musicId: String): MumentDetailSideEffect()
        data class NavToMumentHistory(val musicId: String): MumentDetailSideEffect()
        data class EditMument(val mumentId: String): MumentDetailSideEffect()
        object SuccessMumentDeletion: MumentDetailSideEffect()
        object FailMumentDeletion: MumentDetailSideEffect()
    }

}