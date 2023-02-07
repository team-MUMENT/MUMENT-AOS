package com.mument_android.detail.mument.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiStatus
import com.mument_android.core_dependent.base.MviViewModel
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.util.*
import com.mument_android.detail.BuildConfig
import com.mument_android.detail.R
import com.mument_android.detail.mument.contract.MumentDetailContract.*
import com.mument_android.domain.usecase.detail.*
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MumentDetailViewModel @Inject constructor(
    private val fetchMumentDetailContentUseCase: FetchMumentDetailContentUseCase,
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase,
    private val fetchUsersLikeMumentUseCase: FetchUsersLikeMumentUseCase,
    private val deleteMumentUseCase: DeleteMumentUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val dataStoreManager: DataStoreManager,
    private val mediaUtils: MediaUtils
) : MviViewModel<MumentDetailEvent, MumentDetailViewState, MumentDetailSideEffect>() {
    override fun setInitialState(): MumentDetailViewState  = MumentDetailViewState()

    override fun handleEvents(event: MumentDetailEvent) {
        when(event) {
            MumentDetailEvent.OnClickBackButton -> setEffect { MumentDetailSideEffect.PopBackStack }

            is MumentDetailEvent.ReceiveMumentId -> {
                setState { copy(requestMumentId = event.mumentId) }
                fetchMumentDetailContent(event.mumentId)
                fetchLikeUserList(event.mumentId)
            }
            is MumentDetailEvent.ReceiveMusicInfo -> {
                setState { copy(musicInfo = event.musicInfoEntity) }
                fetchMumentList(event.musicInfoEntity.id)
            }

            MumentDetailEvent.OnClickOptionButton -> {
                setEffect {
                    if (viewState.value.isWriter) MumentDetailSideEffect.OpenEditOrDeleteMumentDialog else MumentDetailSideEffect.OpenBlockOrReportBottomSheet
                }
            }

            MumentDetailEvent.SelectBlockUserType -> setEffect { MumentDetailSideEffect.OpenBlockUserDialog }
            MumentDetailEvent.SelectMumentDeletionType -> setEffect { MumentDetailSideEffect.OpenDeleteMumentDialog }
            MumentDetailEvent.SelectReportMumentType -> setEffect { MumentDetailSideEffect.NavToReportMument }
            MumentDetailEvent.OnClickBlockUser -> { blockUser() }

            is MumentDetailEvent.SelectMumentEditType -> setEffect { MumentDetailSideEffect.NavToEditMument(event.mument) }
            MumentDetailEvent.OnClickDeleteMument -> deleteMument()

            MumentDetailEvent.OnClickLikeMument -> likeMument()
            MumentDetailEvent.OnClickUnLikeMument -> cancelLikeMument()

            is MumentDetailEvent.OnClickAlum -> setEffect { MumentDetailSideEffect.NavToMusicDetail(event.musicId) }

            is MumentDetailEvent.OnClickHistory -> setEffect { MumentDetailSideEffect.NavToMumentHistory(event.musicId) }

            is MumentDetailEvent.OnClickShareMument -> {
                event.mumentEntity?.let { mument ->
                    event.musicInfo?.let { music ->
                        setEffect { MumentDetailSideEffect.OpenShareMumentDialog(mument, music) }
                    }
                } ?: setEffect { MumentDetailSideEffect.Toast(R.string.cannot_access_insta) }
            }
            is MumentDetailEvent.UpdateMumentToShareInstagram -> { setState { copy(mument = event.mument) } }
            is MumentDetailEvent.OnDismissShareMumentDialog -> {
                setEffect { MumentDetailSideEffect.NavToInstagram(event.imageUri) }
                setState { copy(fileToShare = event.imageFile) }
            }
            MumentDetailEvent.EntryFromInstagram -> deleteSharedImageFile()
        }
    }

    fun updateRenderedProfileImage(completed: Boolean) {
        setState { copy(renderedProfileImage = completed) }
    }

    fun updateRenderedAlbumCover(completed: Boolean) {
        setState { copy(renderdAlbumCover = completed) }
    }

    fun updateRenderedTags(completed: Boolean) {
        setState { copy(renderedTags = completed) }
    }

    private fun likeMument() {
        viewModelScope.launch {
            changeLikeStatus(true)
            likeMumentUseCase(viewState.value.requestMumentId)
                .catch {
                    changeLikeStatus(false)
                }.collect {}
        }
    }

    private fun cancelLikeMument() {
        viewModelScope.launch {
            changeLikeStatus(false)
            cancelLikeMumentUseCase(viewState.value.requestMumentId)
                .catch {
                    changeLikeStatus(true)
                }.collect {}
        }
    }

    private fun changeLikeStatus(like: Boolean) {
        if (like) {
            setState { copy(likeCount = likeCount + 1 ) }
            setState { copy(isLikedMument = true) }
        } else {
            setState { copy(likeCount = likeCount - 1 ) }
            setState { copy(isLikedMument = false) }
        }
    }

    private fun fetchMumentDetailContent(mumentId: String) {
        viewModelScope.launch {
            fetchMumentDetailContentUseCase(mumentId).collect { status ->
                when(status) {
                    ApiStatus.Loading -> { setState { copy(onNetwork = true) } }
                    is ApiStatus.Failure -> disableFetchData()

                    is ApiStatus.Success -> {
                        val userId = runBlocking { dataStoreManager.userIdFlow.first() ?: "" }
                        status.data.let { mumentDetail ->
                            setState {
                                copy(
                                    isWriter = mumentDetail.mument.writerInfo.userId == userId,
                                    mument = mumentDetail.mument,
                                    isLikedMument = mumentDetail.isLiked,
                                    likeCount = mumentDetail.likeCount,
                                    historyCount = mumentDetail.mumentHistoryCount,
                                    onNetwork = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun disableFetchData() {
        setState { copy(hasError= true, onNetwork = false) }
        setEffect { MumentDetailSideEffect.Toast(R.string.cannot_load_data) }
    }

    private fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(musicId, "Y")
                .catch { e -> }
                .collect {
                    setState {
                        copy(hasWrittenMument = it.map { it.user.userId }.contains(BuildConfig.USER_ID))
                    }
                }
        }
    }

    private fun deleteMument() {
        viewModelScope.launch {
            deleteMumentUseCase(viewState.value.requestMumentId).collect { status ->
                if (status is ApiStatus.Success) {
                    setEffect { MumentDetailSideEffect.SuccessMumentDeletion }

                }
            }
        }
    }

    private fun blockUser() {
        viewModelScope.launch {
            blockUserUseCase(viewState.value.requestMumentId)
                .collect {

                }
        }
    }

    private fun deleteSharedImageFile() {
        val file = viewState.value.fileToShare
        setState { copy(fileToShare = null) }
        file?.delete()
    }

    private fun fetchLikeUserList(mumentId: String) {
        viewModelScope.launch {
            fetchUsersLikeMumentUseCase(mumentId, 30, 0).collect { status ->
                if (status is ApiStatus.Success) {
                    setState { copy(likeUsers = status.data) }
                }
            }
        }
    }

    companion object {
        private const val FILE_NAME_TO_SHARE = "MumentShareImage"
    }
}