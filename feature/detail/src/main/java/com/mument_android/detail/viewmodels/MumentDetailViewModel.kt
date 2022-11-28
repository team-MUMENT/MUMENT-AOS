package com.mument_android.detail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.util.emitUserEvent
import com.mument_android.core_dependent.util.handleUserEvent
import com.mument_android.core_dependent.util.setEffect
import com.mument_android.core_dependent.util.setState
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
    private val deleteMumentUseCase: DeleteMumentUseCase
) : ViewModel() {
    private val _viewState = MutableStateFlow(MumentDetailViewState())
    val viewState = _viewState.asStateFlow()

    private val _effect: Channel<MumentDetailSideEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _userEvent: MutableSharedFlow<MumentDetailEvent> = MutableSharedFlow()

    init {
        handleUserEvent()
    }

    fun emitUserEvent(event: MumentDetailEvent) {
        _userEvent.emitUserEvent(viewModelScope, event)
    }
    
    private fun setEffect(effect: MumentDetailSideEffect, listener: (() -> Unit)? = null) {
        _effect.setEffect(viewModelScope) { effect }
        listener?.let { it() }
    }

    private fun handleUserEvent() {
        _userEvent.asSharedFlow().handleUserEvent(viewModelScope) { event ->
            when(event) {
                MumentDetailEvent.OnClickLikeMument -> likeMument()
                MumentDetailEvent.OnClickUnLikeMument -> cancelLikeMument()
                MumentDetailEvent.OnClickDeleteMument -> deleteMument()
                MumentDetailEvent.OnClickBackIcon -> setEffect(MumentDetailSideEffect.PopBackStack)
                is MumentDetailEvent.OnClickAlum -> setEffect(MumentDetailSideEffect.NavToMusicDetail(event.musicId))
                is MumentDetailEvent.OnClickHistory -> setEffect(MumentDetailSideEffect.NavToMumentHistory(event.musicId))
                is MumentDetailEvent.OnClickEditMument -> setEffect(MumentDetailSideEffect.EditMument(event.mument))
            }
        }
    }


    fun updateRequestMumentId(id: String) {
        _viewState.setState { copy(requestMumentId = id) }
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
            }.collect { mumentDetail ->
                _viewState.setState {
                    if (mumentDetail == null) {
                        copy(hasError = true, onNetwork = false)
                    } else {
                        fetchMumentList(mumentDetail.mument.musicInfo.id)
                        copy(
                            isWriter = mumentDetail.mument.writerInfo.userId == BuildConfig.USER_ID,
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
                    _effect.setEffect(viewModelScope) { MumentDetailSideEffect.FailMumentDeletion }
                }.collect {
                    _effect.setEffect(viewModelScope) { MumentDetailSideEffect.SuccessMumentDeletion }
                }
        }
    }
}