package com.mument_android.detail.mument

import android.net.Uri
import android.view.View
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.Event
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

    sealed class MumentDetailEvent: Event {
        data class ReceiveMumentId(val mumentId: String): MumentDetailEvent()
        data class OnClickAlum(val musicId: String): MumentDetailEvent()
        data class OnClickHistory (val musicId: String): MumentDetailEvent()
        data class OnClickEditMument (val mument: String): MumentDetailEvent()
        data class OnClickShareMument(val mumentView: View): MumentDetailEvent()
        object OnClickBackIcon: MumentDetailEvent()
        object OnClickLikeMument: MumentDetailEvent()
        object OnClickUnLikeMument: MumentDetailEvent()
        object OnClickDeleteMument: MumentDetailEvent()
    }

    sealed class MumentDetailSideEffect: SideEffect {
        object PopBackStack: MumentDetailSideEffect()
        object SuccessMumentDeletion: MumentDetailSideEffect()
        data class Toast(val message: String): MumentDetailSideEffect()
        data class NavToMusicDetail(val musicId: String): MumentDetailSideEffect()
        data class NavToMumentHistory(val musicId: String): MumentDetailSideEffect()
        data class EditMument(val mumentId: String): MumentDetailSideEffect()
        data class ShowShareOptions(val fileUri: Uri?): MumentDetailSideEffect()
    }
}