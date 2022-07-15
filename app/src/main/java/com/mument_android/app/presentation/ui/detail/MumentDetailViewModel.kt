package com.mument_android.app.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.usecase.detail.FetchMumentDetailContentUseCase
import com.mument_android.app.domain.usecase.main.CancelLikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MumentDetailViewModel @Inject constructor(
    private val fetchMumentDetailContentUseCase: FetchMumentDetailContentUseCase,
    private val likeMumentUseCase: CancelLikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase
): ViewModel() {
    private val _mumentId = MutableStateFlow("")
    val mumentId = _mumentId.asStateFlow()

    private val _mumentDetailContent = MutableStateFlow<ApiResult<MumentDetailEntity>?>(null)
    val mumentDetailContent = _mumentDetailContent.asStateFlow()

    init {
        fetchMumentDetailContent()
    }

    fun changeMumentId(id: String) {
        _mumentId.value = id
    }

    private fun fetchMumentDetailContent() {
        viewModelScope.launch {
            fetchMumentDetailContentUseCase("", "").onStart {
                _mumentDetailContent.value = ApiResult.Loading(null)
            }.catch {
                _mumentDetailContent.value = ApiResult.Loading(null)
            }.collect {
                _mumentDetailContent.value = ApiResult.Success(it)
            }
        }
    }

    fun likeMument() {
        viewModelScope.launch {
            likeMumentUseCase(
                mumentId.value,
                mumentDetailContent.value?.data?.writerInfo?.userId ?: ""
            )
        }
    }

    fun cancelLikeMument() {
        viewModelScope.launch {
            cancelLikeMumentUseCase(
                mumentId.value,
                mumentDetailContent.value?.data?.writerInfo?.userId ?: ""
            )
        }
    }
}