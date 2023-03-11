package com.mument_android.detail.mument.viewmodel

import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiStatus
import com.mument_android.core.network.ErrorMessage
import com.mument_android.core_dependent.base.MviViewModel
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.detail.R
import com.mument_android.detail.mument.contract.MumentDetailContract.*
import com.mument_android.domain.usecase.detail.BlockUserUseCase
import com.mument_android.domain.usecase.detail.DeleteMumentUseCase
import com.mument_android.domain.usecase.detail.FetchMumentDetailContentUseCase
import com.mument_android.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MumentDetailViewModel @Inject constructor(
    private val fetchMumentDetailContentUseCase: FetchMumentDetailContentUseCase,
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase,
    private val deleteMumentUseCase: DeleteMumentUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val dataStoreManager: DataStoreManager
) : MviViewModel<MumentDetailEvent, MumentDetailViewState, MumentDetailSideEffect>() {
    private var mumentId: String? = null
    val isAdmin = MutableStateFlow<Boolean>(false)
    override fun setInitialState(): MumentDetailViewState = MumentDetailViewState()

    override fun handleEvents(event: MumentDetailEvent) {
        when (event) {
            MumentDetailEvent.OnClickLikeCount -> {
                if (mumentId != null) {
                    setEffect {
                        MumentDetailSideEffect.NavToLikeUserListView(mumentId!!)
                    }
                }
            }
            MumentDetailEvent.OnClickBackButton -> setEffect { MumentDetailSideEffect.PopBackStack }

            is MumentDetailEvent.ReceiveMumentId -> {
                setState { copy(requestMumentId = event.mumentId) }
                fetchMumentDetailContent(event.mumentId)
                mumentId = event.mumentId
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
            MumentDetailEvent.SelectReportMumentType -> setEffect {
                MumentDetailSideEffect.NavToReportMument(
                    viewState.value.requestMumentId
                )
            }
            MumentDetailEvent.OnClickBlockUser -> {
                blockUser()
            }
            MumentDetailEvent.OnClickBlockUser -> {
                blockUser()
            }

            is MumentDetailEvent.SelectMumentEditType -> setEffect {
                MumentDetailSideEffect.NavToEditMument(
                    event.mumentId,
                    event.mumentModifyEntity,
                    event.music
                )
            }
            MumentDetailEvent.OnClickDeleteMument -> deleteMument()

            MumentDetailEvent.OnClickLikeMument -> likeMument()
            MumentDetailEvent.OnClickUnLikeMument -> cancelLikeMument()

            is MumentDetailEvent.OnClickAlum -> setEffect {
                MumentDetailSideEffect.NavToMusicDetail(
                    event.music
                )
            }
            is MumentDetailEvent.OnClickHistory -> setEffect {
                MumentDetailSideEffect.NavToMumentHistory(
                    event.musicId
                )
            }
            is MumentDetailEvent.OnClickShareMument -> {
                event.mumentEntity?.let { mument ->
                    event.musicInfo?.let { music ->
                        setEffect { MumentDetailSideEffect.OpenShareMumentDialog(mument, music) }
                    }
                } ?: setEffect { MumentDetailSideEffect.Toast(R.string.cannot_access_insta) }
            }
            is MumentDetailEvent.UpdateMumentToShareInstagram -> {
                setState { copy(mument = event.mument, musicInfo = event.musicInfo) }
            }
            is MumentDetailEvent.OnDismissShareMumentDialog -> {
                setEffect { MumentDetailSideEffect.NavToInstagram(event.imageUri) }
                setState { copy(fileToShare = event.imageFile) }
            }
            MumentDetailEvent.EntryFromInstagram -> deleteSharedImageFile()
            is MumentDetailEvent.ReceiveStartNav -> setState { copy(navStart = event.startNav) }
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
            likeMumentUseCase(viewState.value.requestMumentId)
                .catch {
                    changeLikeStatus(false)
                }.collect {
                    delay(1000)
                    changeLikeStatus(true)
                }
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
            setState { copy(likeCount = likeCount + 1) }
            setState { copy(isLikedMument = true) }
        } else {
            setState { copy(likeCount = likeCount - 1) }
            setState { copy(isLikedMument = false) }
        }
    }

    private fun fetchMumentDetailContent(mumentId: String) {
        viewModelScope.launch {
            checkIsAdmin(mumentId)
            fetchMumentDetailContentUseCase(mumentId).collect { status ->
                when (status) {
                    ApiStatus.Loading -> {
                        setState { copy(onNetwork = true) }
                    }
                    is ApiStatus.Failure -> {
                        if (status.message == "Unknown") {
                            setEffect { MumentDetailSideEffect.ShowDeletedMumentAlert }
                        }
                        /*disableFetchData()*/
                    }

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

    private suspend fun checkIsAdmin(mumentId: String) {
        isAdmin.value = dataStoreManager.adminUserList.firstOrNull()
            ?.any { it == mumentId } ?: false
    }

    private fun disableFetchData() {
        setState { copy(hasError = true, onNetwork = false) }
        setEffect { MumentDetailSideEffect.Toast(R.string.cannot_load_data) }
    }

    private fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            val userId = runBlocking { dataStoreManager.userIdFlow.first() ?: "" }
            fetchMumentListUseCase(musicId, "Y")
                .catch { e -> }
                .collect {
                    setState {
                        copy(hasWrittenMument = it.map { it.user.userId }
                            .contains(userId))
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
                .collect { status ->
                    when (status) {
                        is ApiStatus.Failure -> {
                            when (status.code) {
                                ErrorMessage.INVALID -> {
                                    setEffect { MumentDetailSideEffect.ToastString(status.message!!) }
                                }
                                else -> {}
                            }
                        }
                        is ApiStatus.Success -> {
                            setEffect { MumentDetailSideEffect.SuccessBlockUser }
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun deleteSharedImageFile() {
        val file = viewState.value.fileToShare
        setState { copy(fileToShare = null) }
        file?.delete()
    }


    companion object {
        private const val FILE_NAME_TO_SHARE = "MumentShareImage"
    }
}