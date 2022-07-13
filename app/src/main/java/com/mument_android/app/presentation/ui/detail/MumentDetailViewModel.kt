package com.mument_android.app.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.usecase.detail.FetchMumentDetailContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MumentDetailViewModel @Inject constructor(
    private val fetchMumentDetailContentUseCase: FetchMumentDetailContentUseCase
): ViewModel() {
    private val _emotionalTag = MutableStateFlow<Int>(R.string.emotional_blue)
    val emotionalTag = _emotionalTag.asStateFlow()

    private val _mumentDetailContent = MutableStateFlow<ApiResult<MumentDetailEntity>?>(null)
    val mumentDetailContent = _mumentDetailContent.asStateFlow()

    init {
        _emotionalTag.value = EmotionalTag.findEmotionalStringTag(1)
        fetchMumentDetailContent()
    }

    fun setRandomTags() {
        _emotionalTag.value = EmotionalTag.values().random().tag

    }

    private fun fetchMumentDetailContent() {
        viewModelScope.launch {
            fetchMumentDetailContentUseCase(1, 1).stateIn(
                initialValue = ApiResult.Loading(null),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            ).collect {
                _mumentDetailContent.value = it
            }
        }
    }
}