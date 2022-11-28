package com.mument_android.detail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.util.setState
import com.mument_android.detail.BuildConfig
import com.mument_android.detail.mument.MumentDetailContract
import com.mument_android.domain.usecase.detail.DeleteMumentUseCase
import com.mument_android.domain.usecase.detail.FetchMumentDetailContentUseCase
import com.mument_android.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MumentDetailViewModel @Inject constructor(
    private val fetchMumentDetailContentUseCase: FetchMumentDetailContentUseCase,
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase,
    private val deleteMumentUseCase: DeleteMumentUseCase
) : ViewModel() {
    val isLiked = MutableStateFlow<Boolean>(false)

    private val _viewState = MutableStateFlow(MumentDetailContract.MumentDetailViewState())
    val viewState = _viewState.asStateFlow()

    private val _hasWritten = MutableLiveData<Boolean>()
    val hasWritten: LiveData<Boolean> = _hasWritten

    private val _backStack = MutableStateFlow<String>("")
    val backStack = _backStack.asStateFlow()

    private val _mumentId = MutableStateFlow("")
    val mumentId = _mumentId.asStateFlow()

    private val _successDelete = MutableSharedFlow<Unit>()
    val successDelete = _successDelete.asSharedFlow()

    val notifyContent =  MutableLiveData<String>()

    fun changeBackStack(backstack: String) {
        _backStack.value = backstack
    }

    fun changeMumentId(id: String) {
        _mumentId.value = id
    }

    private fun increaseLikeCount() {
        _viewState.value = MumentDetailContract.MumentDetailViewState(likeCount = viewState.value.likeCount + 1)
    }

    private fun decreaseLikeCount() {
        _viewState.value = MumentDetailContract.MumentDetailViewState(likeCount = viewState.value.likeCount - 1)
    }

    fun fetchMumentDetailContent(mumentId: String) {
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
                        checkMumentHasWritten(mumentDetail.mument.musicInfo.id)
                        copy(
                            mument = mumentDetail.mument,
                            isLiked = mumentDetail.isLiked,
                            likeCount = mumentDetail.likeCount,
                            mumentHistoryCount = mumentDetail.mumentHistoryCount,
                            onNetwork = false
                        )
                    }
                }
            }
        }
    }

    private fun checkMumentHasWritten(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(musicId, BuildConfig.USER_ID, "Y")
                .catch { e ->  }
                .collect {
                    _hasWritten.value = it.map { it.user.userId }.contains(BuildConfig.USER_ID)
                }
        }
    }

    fun likeMument() {
        viewModelScope.launch {
            increaseLikeCount()
            likeMumentUseCase(
                mumentId.value,
                BuildConfig.USER_ID
            ).collect {

            }
        }
    }

    fun cancelLikeMument() {
        viewModelScope.launch {
            decreaseLikeCount()
            cancelLikeMumentUseCase(
                mumentId.value,
                BuildConfig.USER_ID
            ).collect {}
        }
    }

    fun deleteMument() {
        viewModelScope.launch {
            deleteMumentUseCase(mumentId.value).catch { e ->
                _successDelete.emit(Unit)
                //Todo exception handling
            }.collect {
                _successDelete.emit(Unit)
            }
        }
    }
}