package com.mument_android.detail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.usecase.detail.DeleteMumentUseCase
import com.mument_android.domain.usecase.detail.FetchMumentDetailContentUseCase
import com.mument_android.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import com.mument_android.core.network.ApiResult
import com.mument_android.detail.BuildConfig
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

    private val _hasWritten = MutableLiveData<Boolean>()
    val hasWritten: LiveData<Boolean> = _hasWritten

    private val _backStack = MutableStateFlow<String>("")
    val backStack = _backStack.asStateFlow()

    private val _mumentId = MutableStateFlow("")
    val mumentId = _mumentId.asStateFlow()

    private val _mumentDetailContent =
        MutableStateFlow<ApiResult<MumentDetailEntity?>>(ApiResult.Idle(null))
    val mumentDetailContent = _mumentDetailContent.asStateFlow()

    private val _likeCount = MutableStateFlow(0)
    val likeCount = _likeCount.asStateFlow()

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
        _likeCount.value = likeCount.value + 1
    }

    private fun decreaseLikeCount() {
        _likeCount.value = likeCount.value - 1
    }

    fun fetchMumentDetailContent(mumentId: String) {
        viewModelScope.launch {
            fetchMumentDetailContentUseCase(mumentId, BuildConfig.USER_ID).onStart {
                _mumentDetailContent.value = ApiResult.Loading(null)
            }.catch { e ->
                _mumentDetailContent.value = ApiResult.Failure(e)
            }.collect {
                _mumentDetailContent.value = ApiResult.Success(it)
                isLiked.value = it?.isLiked ?: false
                _likeCount.value = it?.likeCount ?: 0
                checkMumentHasWritten(it?.musicInfo?.id ?: "")
            }
        }
    }

    fun checkMumentHasWritten(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(musicId, BuildConfig.USER_ID, "Y")
                .catch { e -> //Todo exception handling
                }
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
                mumentDetailContent.value.data?.writerInfo?.userId ?: ""
            ).collect {

            }
        }
    }

    fun cancelLikeMument() {
        viewModelScope.launch {
            decreaseLikeCount()
            cancelLikeMumentUseCase(
                mumentId.value,
                mumentDetailContent.value.data?.writerInfo?.userId ?: ""
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