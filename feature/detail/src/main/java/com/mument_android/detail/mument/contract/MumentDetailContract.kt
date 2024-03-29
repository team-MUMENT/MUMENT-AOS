package com.mument_android.detail.mument.contract

import android.net.Uri
import androidx.annotation.StringRes
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.Event
import com.mument_android.core.util.ViewState
import com.mument_android.domain.entity.detail.MumentEntity
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.record.MumentModifyEntity
import com.mument_android.domain.entity.user.UserEntity
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
        val fileToShare: File? = null,
        val navStart: String = ""
    ) : ViewState

    sealed class MumentDetailEvent : Event {
        object OnClickBackButton : MumentDetailEvent()

        /** 뷰 진입시 Arguments로 MumentId, Music 데이터 업데이트 **/
        data class ReceiveMumentId(val mumentId: String) : MumentDetailEvent()
        data class ReceiveMusicInfo(val musicInfoEntity: MusicInfoEntity) : MumentDetailEvent()
        data class ReceiveStartNav(val startNav: String): MumentDetailEvent()
        data class UpdateMumentInfo(val mumentId: String, val musicInfo: MusicInfoEntity): MumentDetailEvent()

        /** ...메뉴 버튼 클릭 Event **/
        object OnClickOptionButton : MumentDetailEvent()

        /** Mument 수정하기 & 삭제하기 관련 Event **/
        data class SelectMumentEditType(
            val mumentId: String,
            val mumentModifyEntity: MumentModifyEntity,
            val music: RecentSearchData
        ) : MumentDetailEvent()

        object SelectMumentDeletionType : MumentDetailEvent()
        object OnClickDeleteMument : MumentDetailEvent()

        /** 유저 차단하기 & 뮤멘트 신고하기 관련 Event **/
        object SelectReportMumentType : MumentDetailEvent()
        object SelectBlockUserType : MumentDetailEvent()
        object OnClickBlockUser : MumentDetailEvent()

        /** Mument 좋아요 등록 및 해제 Event **/
        object OnClickLikeMument : MumentDetailEvent()
        object OnClickUnLikeMument : MumentDetailEvent()

        /** 앨범 정보 클릭 Event **/
        data class OnClickAlum(val music: MusicInfoEntity, val startNav: String?) : MumentDetailEvent()

        /** 해당 곡의 모든 뮤멘트 보러가기 하단 Floating 클릭 Event **/
        data class OnClickHistory(val musicId: String) : MumentDetailEvent()

        /** Instagram 공유 관련 Event **/
        data class OnClickShareMument(
            val mumentEntity: MumentEntity?,
            val musicInfo: MusicInfoEntity?
        ) : MumentDetailEvent()

        data class OnDismissShareMumentDialog(val imageFile: File, val imageUri: Uri) :
            MumentDetailEvent()

        data class UpdateMumentToShareInstagram(
            val mument: MumentEntity,
            val musicInfo: MusicInfoEntity?
        ) : MumentDetailEvent()

        object EntryFromInstagram : MumentDetailEvent()
        object OnClickLikeCount : MumentDetailEvent()
    }

    sealed class MumentDetailSideEffect : SideEffect {
        object PopBackStack : MumentDetailSideEffect()
        object ShowDeletedMumentAlert : MumentDetailSideEffect()
        data class Toast(@StringRes val message: Int) : MumentDetailSideEffect()
        data class ToastString(val message: String) : MumentDetailSideEffect()
        object SuccessMumentDeletion : MumentDetailSideEffect()
        object SuccessBlockUser : MumentDetailSideEffect()
        object OnCompleteLikeOrUnLikeLogic : MumentDetailSideEffect()

        object OpenEditOrDeleteMumentDialog : MumentDetailSideEffect()
        object OpenBlockOrReportBottomSheet : MumentDetailSideEffect()
        object OpenDeleteMumentDialog : MumentDetailSideEffect()
        object OpenBlockUserDialog : MumentDetailSideEffect()

        data class NavToReportMument(val mumentId: String) : MumentDetailSideEffect()
        data class NavToMusicDetail(val music: MusicInfoEntity) : MumentDetailSideEffect()
        data class NavToMumentHistory(val musicId: String) : MumentDetailSideEffect()
        data class NavToLikeUserListView(val mumentId: String) : MumentDetailSideEffect()

        data class NavToEditMument(
            val mumentId: String,
            val mumentModifyEntity: MumentModifyEntity,
            val music: RecentSearchData
        ) : MumentDetailSideEffect()

        data class OpenShareMumentDialog(val mument: MumentEntity, val musicInfo: MusicInfoEntity) :
            MumentDetailSideEffect()

        data class NavToInstagram(val imageUri: Uri) : MumentDetailSideEffect()
    }
}