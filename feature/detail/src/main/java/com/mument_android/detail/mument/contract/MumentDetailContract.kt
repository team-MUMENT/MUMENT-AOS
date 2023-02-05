package com.mument_android.detail.mument.contract

import android.net.Uri
import androidx.annotation.StringRes
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.Event
import com.mument_android.core.util.ViewState
import com.mument_android.domain.entity.detail.MumentEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import java.io.File

class MumentDetailContract {
    data class MumentDetailViewState(
        override val hasError: Boolean = false,
        override val onNetwork: Boolean = false,
        val requestMumentId: String = "",
        val musicInfo: MusicInfoEntity? = null,
        val isWriter: Boolean = false,
        val mument: MumentEntity? = null,
        val isLikedMument: Boolean = false,
        val likeCount: Int = 0,
        val historyCount: Int = 0,
        val hasWrittenMument: Boolean = false,
        val renderedProfileImage: Boolean = false,
        val renderedTags: Boolean = false,
        val renderdAlbumCover: Boolean = false,
        val fileToShare: File? = null
    ): ViewState

    sealed class MumentDetailEvent: Event {
        data class ReceiveMumentId(val mumentId: String): MumentDetailEvent()
        data class ReceiveMusicInfo(val musicInfoEntity: MusicInfoEntity): MumentDetailEvent()
        data class OnClickAlum(val musicId: String): MumentDetailEvent()
        data class OnClickHistory (val musicId: String): MumentDetailEvent()
        data class OnClickEditMument (val mument: String): MumentDetailEvent()
        data class OnClickShareMument(val mumentEntity: MumentEntity?, val musicInfo: MusicInfoEntity?): MumentDetailEvent()
        data class OnDismissShareMumentDialog(val imageFile: File, val imageUri: Uri): MumentDetailEvent()
        object OnClickBackIcon: MumentDetailEvent()
        object OnClickLikeMument: MumentDetailEvent()
        object OnClickUnLikeMument: MumentDetailEvent()
        object OnClickDeleteMument: MumentDetailEvent()
        object EntryFromInstagram: MumentDetailEvent()

        // Instagram 공유하기 전 노출되는 Dialog에 Mument 업데이트 하기 위한 Event
        data class UpdateMumentToShareInstagram(val mument: MumentEntity, val musicInfo: MusicInfoEntity?): MumentDetailEvent()
    }

    sealed class MumentDetailSideEffect: SideEffect {
        object PopBackStack: MumentDetailSideEffect()
        object SuccessMumentDeletion: MumentDetailSideEffect()
        data class Toast(@StringRes val message: Int): MumentDetailSideEffect()
        data class NavToMusicDetail(val musicId: String): MumentDetailSideEffect()
        data class NavToMumentHistory(val musicId: String): MumentDetailSideEffect()
        data class EditMument(val mumentId: String): MumentDetailSideEffect()
        data class OpenShareMumentDialog(val mument: MumentEntity, val musicInfo: MusicInfoEntity): MumentDetailSideEffect()
        data class NavToInstagram(val imageUri: Uri): MumentDetailSideEffect()
    }
}