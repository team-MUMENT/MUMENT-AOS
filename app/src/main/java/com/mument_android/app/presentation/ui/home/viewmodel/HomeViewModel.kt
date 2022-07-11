package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.mument_android.R
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.EmotionalTag.Companion.findEmotionalTag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {
    private val _emotionalTag = MutableStateFlow<Int>(R.string.emotional_blue)
    val emotionalTag = _emotionalTag.asStateFlow()

    init {
        _emotionalTag.value = findEmotionalTag(1)
    }

    fun setRandomTags() {
        _emotionalTag.value = EmotionalTag.values().random().tag
    }
}