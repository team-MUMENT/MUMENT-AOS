package com.mument_android.detail.mument.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.util.*
import com.mument_android.detail.BuildConfig
import com.mument_android.detail.R
import com.mument_android.domain.usecase.detail.DeleteMumentUseCase
import com.mument_android.domain.usecase.detail.FetchMumentDetailContentUseCase
import com.mument_android.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mument_android.detail.mument.contract.MumentDetailContract.*
import com.mument_android.domain.usecase.detail.BlockUserUseCase
import kotlinx.coroutines.runBlocking

@HiltViewModel
class MumentDetailViewModel @Inject constructor(
    private val fetchMumentDetailContentUseCase: FetchMumentDetailContentUseCase,
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase,
    private val deleteMumentUseCase: DeleteMumentUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val dataStoreManager: DataStoreManager,
    private val mediaUtils: MediaUtils
) : ViewModel() {
    private val _viewState = MutableStateFlow(MumentDetailViewState())
    val viewState = _viewState.asStateFlow()

    private val _effect: Channel<MumentDetailSideEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _event: MutableSharedFlow<MumentDetailEvent> = MutableSharedFlow()

    init {
        collectEvent()
    }

    fun emitEvent(event: MumentDetailEvent) {
        _event.emitEvent(viewModelScope, event)
    }

    private fun emitEffect(effect: MumentDetailSideEffect) {
        _effect.emitEffect(viewModelScope) { effect }
    }

    fun updateRenderedProfileImage(completed: Boolean) {
        _viewState.setState { copy(renderedProfileImage = completed) }
    }

    fun updateRenderedAlbumCover(completed: Boolean) {
        _viewState.setState { copy(renderdAlbumCover = completed) }
    }

    fun updateRenderedTags(completed: Boolean) {
        _viewState.setState { copy(renderedTags = completed) }
    }

    private fun collectEvent() {
        _event.asSharedFlow().collectEvent(viewModelScope) { event ->
            when(event) {
                MumentDetailEvent.OnClickLikeMument -> likeMument()
                MumentDetailEvent.OnClickUnLikeMument -> cancelLikeMument()
                MumentDetailEvent.OnClickDeleteMument -> deleteMument()
                MumentDetailEvent.OnClickBackIcon -> emitEffect(MumentDetailSideEffect.PopBackStack)
                is MumentDetailEvent.OnClickAlum -> emitEffect(MumentDetailSideEffect.NavToMusicDetail(event.musicId))
                is MumentDetailEvent.OnClickHistory -> emitEffect(MumentDetailSideEffect.NavToMumentHistory(event.musicId))
                is MumentDetailEvent.OnClickEditMument -> emitEffect(MumentDetailSideEffect.EditMument(event.mument))
                is MumentDetailEvent.ReceiveMumentId -> {
                    _viewState.setState { copy(requestMumentId = event.mumentId) }
                    fetchMumentDetailContent(event.mumentId)
                }
                is MumentDetailEvent.ReceiveMusicInfo -> {
                    _viewState.setState { copy(musicInfo = event.musicInfoEntity) }
                    fetchMumentList(event.musicInfoEntity.id)
                }
                is MumentDetailEvent.OnClickShareMument -> {
                    event.mumentEntity?.let { mument ->
                        emitEffect(MumentDetailSideEffect.OpenShareMumentDialog(mument))
                    } ?: emitEffect(MumentDetailSideEffect.Toast(R.string.cannot_access_insta))
                }
                is MumentDetailEvent.UpdateMumentToShareInstagram -> { _viewState.setState { copy(mument = event.mument) } }
                is MumentDetailEvent.OnDismissShareMumentDialog -> {
                    emitEffect(MumentDetailSideEffect.NavToInstagram(event.imageUri))
                    _viewState.setState { copy(fileToShare = event.imageFile) }
                }
                MumentDetailEvent.EntryFromInstagram -> deleteSharedImageFile()
            }
        }
    }

    private fun likeMument() {
        viewModelScope.launch {
            _viewState.setState { copy(likeCount = likeCount + 1 ) }
            likeMumentUseCase(viewState.value.requestMumentId, BuildConfig.USER_ID)
                .catch { }
                .collect {
                    _viewState.setState { copy(isLikedMument = true) }
                }
        }
    }

    private fun cancelLikeMument() {
        viewModelScope.launch {
            _viewState.setState { copy(likeCount = likeCount - 1 ) }
            cancelLikeMumentUseCase(viewState.value.requestMumentId, BuildConfig.USER_ID)
                .catch { }
                .collect {
                    _viewState.setState { copy(isLikedMument = false) }
                }
        }
    }

    private fun fetchMumentDetailContent(mumentId: String) {
        viewModelScope.launch {
            fetchMumentDetailContentUseCase(mumentId).onStart {
                _viewState.setState { copy(onNetwork = true) }
            }.catch { e ->
                _viewState.setState { copy(hasError= true, onNetwork = false) }
                emitEffect(MumentDetailSideEffect.Toast(R.string.cannot_load_data))
            }.collect { mumentDetail ->
                val userId = runBlocking {
                    dataStoreManager.userIdFlow.first() ?: ""
                }
                mumentDetail?.let {
                    _viewState.setState {
                        copy(
                            isWriter = mumentDetail.mument.writerInfo.userId == userId,
                            mument = mumentDetail.mument,
                            isLikedMument = mumentDetail.isLiked,
                            likeCount = mumentDetail.likeCount,
                            historyCount = mumentDetail.mumentHistoryCount,
                            onNetwork = false
                        )
                    }
                } ?: disableFetchData()
            }
        }
    }

    private fun disableFetchData() {
        _viewState.setState { copy(hasError= true, onNetwork = false) }
        emitEffect(MumentDetailSideEffect.Toast(R.string.cannot_load_data))
    }

    private fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(musicId, "Y")
                .catch { e ->  }
                .collect {
                    _viewState.setState {
                        copy(hasWrittenMument = it.map { it.user.userId }.contains(BuildConfig.USER_ID))
                    }
                }
        }
    }

    private fun deleteMument() {
        viewModelScope.launch {
            deleteMumentUseCase(viewState.value.requestMumentId)
                .catch {
                    emitEffect(MumentDetailSideEffect.Toast(R.string.fail_to_delete_mument))
                }.collect {
                    emitEffect(MumentDetailSideEffect.SuccessMumentDeletion)
                }
        }
    }

    fun blockUser() {
        viewModelScope.launch {
            blockUserUseCase(viewState.value.requestMumentId)
                .also {
                    Log.e("status", "${it}")
                }
        }
    }

    private fun deleteSharedImageFile() {
        val file = viewState.value.fileToShare
        _viewState.setState { copy(fileToShare = null) }
        file?.delete()
    }

    companion object {
        private const val FILE_NAME_TO_SHARE= "MumentShareImage"
    }
}