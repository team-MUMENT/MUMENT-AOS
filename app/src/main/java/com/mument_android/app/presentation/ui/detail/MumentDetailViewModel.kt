package com.mument_android.app.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MumentDetailViewModel: ViewModel() {
    private val _emotionalTag = MutableStateFlow<Int>(R.string.emotional_blue)
    val emotionalTag = _emotionalTag.asStateFlow()

    val isTagSelected = MutableStateFlow<Boolean>(false)

    init {
        _emotionalTag.value = EmotionalTag.findEmotionalStringTag(1)
    }

    fun setRandomTags() {
        _emotionalTag.value = EmotionalTag.values().random().tag
    }
}