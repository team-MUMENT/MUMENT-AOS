package com.mument_android.detail.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.util.*
import com.mument_android.detail.BuildConfig
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
import com.mument_android.detail.mument.MumentDetailContract.*

@HiltViewModel
class MumentDetailViewModel @Inject constructor(
    private val fetchMumentDetailContentUseCase: FetchMumentDetailContentUseCase,
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase,
    private val deleteMumentUseCase: DeleteMumentUseCase,
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
                    updateRequestMumentId(event.mumentId)
                }
                is MumentDetailEvent.OnClickShareMument -> {
                    val uri = mediaUtils.getBitmapUri(event.mumentView, FILE_NAME_TO_SHARE)
                    emitEffect(MumentDetailSideEffect.ShowShareOptions(uri))
                }
            }
        }
    }

    private fun updateRequestMumentId(id: String) {
        fetchMumentDetailContent(id)
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
                emitEffect(MumentDetailSideEffect.Toast("데이터를 불러올 수 없습니다."))
            }.collect { mumentDetail ->
                mumentDetail?.let {
                    fetchMumentList(mumentDetail.mument.musicInfo.id)
                    _viewState.setState {
                        copy(
                            isWriter = mumentDetail.mument.writerInfo.userId == BuildConfig.USER_ID,
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
        emitEffect(MumentDetailSideEffect.Toast("데이터를 불러올 수 없습니다."))
    }

    private fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(musicId, BuildConfig.USER_ID, "Y")
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
                    emitEffect(MumentDetailSideEffect.Toast("뮤멘트를 삭제하지 못했습니다."))
                }.collect {
                    emitEffect(MumentDetailSideEffect.SuccessMumentDeletion)
                }
        }
    }

    companion object {
        private const val FILE_NAME_TO_SHARE= "MumentShareImage"
    }
}