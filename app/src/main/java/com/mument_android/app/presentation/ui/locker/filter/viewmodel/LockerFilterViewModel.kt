package com.mument_android.app.presentation.ui.locker.filter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.TagEntity
import com.startup.core_dependent.util.EmotionalTag
import com.startup.core_dependent.util.ImpressiveTag

class LockerFilterViewModel: ViewModel() {
    val emotionalTags = EmotionalTag.values().map { TagEntity(TagEntity.TAG_EMOTIONAL, it.tag, it.tagIndex) }
    val impressionTags = ImpressiveTag.values().map { TagEntity(TagEntity.TAG_IMPRESSIVE, it.tag, it.tagIndex) }

    private val _selectedTags = MutableLiveData<List<TagEntity>>(mutableListOf())
    val selectedTags = _selectedTags


    // 좋아요 한 뮤멘트
    private val _likeSelectedTags = MutableLiveData<List<TagEntity>>(mutableListOf())
    val likeSelectedTags = _likeSelectedTags


    fun addInitialTags(tags: List<TagEntity>) {
        selectedTags.value?.toMutableList().let {
            it?.addAll(tags)
            _selectedTags.value = it
        }
    }


    //보관함 내 뮤멘트
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

    fun addLikeInitialTags(tags: List<TagEntity>) {
        likeSelectedTags.value?.toMutableList().let{
            it?.addAll(tags)
            _likeSelectedTags.value = it
        }
    }

    //보관함 내가 좋아요 한 뮤멘트
    fun addLikeSelectedTag(tag: TagEntity) {
        likeSelectedTags.value?.toMutableList()?.let {
            it.add(tag)
            _likeSelectedTags.value = it
        }
    }

    fun removeLikeSelectedTag(tag: TagEntity) {
        likeSelectedTags.value?.toMutableList()?.let {
            it.remove(tag)
            _likeSelectedTags.value = it
        }
    }

    fun clearLikeSelectedTags() {
        _likeSelectedTags.value = emptyList()
    }

}