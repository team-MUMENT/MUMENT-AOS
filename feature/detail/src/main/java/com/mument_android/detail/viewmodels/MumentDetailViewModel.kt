package com.mument_android.detail.viewmodels

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
    private val deleteMumentUseCase: DeleteMumentUseCase
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

    private fun setEffect(effect: MumentDetailSideEffect, listener: (() -> Unit)? = null) {
        _effect.setEffect(viewModelScope) { effect }
        listener?.let { it() }
    }

    private fun collectEvent() {
        _event.asSharedFlow().collectEvent(viewModelScope) { event ->
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
                setEffect(MumentDetailSideEffect.Toast("데이터를 불러올 수 없습니다."))
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
        setEffect(MumentDetailSideEffect.Toast("데이터를 불러올 수 없습니다."))
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
                    setEffect(MumentDetailSideEffect.Toast("뮤멘트를 삭제하지 못했습니다."))
                }.collect {
                    setEffect(MumentDetailSideEffect.SuccessMumentDeletion)
                }
        }
    }
}