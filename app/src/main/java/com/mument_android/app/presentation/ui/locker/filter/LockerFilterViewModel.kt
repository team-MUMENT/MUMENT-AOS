package com.mument_android.app.presentation.ui.locker.filter

import android.nfc.Tag
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.TagEntity

class LockerFilterViewModel: ViewModel() {
    val emotionalTags = EmotionalTag.values().map { TagEntity(TagEntity.TAG_EMOTIONAL, it.tag, it.tagIndex) }
    val impressionTags = ImpressiveTag.values().map { TagEntity(TagEntity.TAG_IMPRESSIVE, it.tag, it.tagIndex) }

    private val _initialSelectedTags = MutableLiveData<List<TagEntity>>()
    val initialSelectedTags = _initialSelectedTags

    private val _selectedTags = MutableLiveData<List<TagEntity>>(mutableListOf())
    val selectedTags = _selectedTags

    fun addSelectedTag(tag: TagEntity) {
        selectedTags.value?.toMutableList()?.let {
            it.add(tag)
            _selectedTags.value = it
        }
    }

    fun removeSelectedTag(tag: TagEntity) {
        selectedTags.value?.toMutableList()?.let {
            it.remove(tag)
            _selectedTags.value = it
        }
    }

    fun clearSelectedTags() {
        _selectedTags.value = emptyList()
    }

    fun changeInitialSelectedTags(tags: List<TagEntity>) {
        _initialSelectedTags.value = tags
    }
}