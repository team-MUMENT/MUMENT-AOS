package com.mument_android.app.presentation.ui.locker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.LockerMumentEntity
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.usecase.locker.FetchMyMumentListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockerViewModel @Inject constructor(
    private val fetchMyMumentListUseCase: FetchMyMumentListUseCase
): ViewModel() {
    val emotionalTags = EmotionalTag.values().map { TagEntity(TagEntity.TAG_EMOTIONAL, it.tag, it.tagIndex) }
    val impressionTags = ImpressiveTag.values().map { TagEntity(TagEntity.TAG_IMPRESSIVE, it.tag, it.tagIndex) }

    private val _myMuments = MutableLiveData<List<LockerMumentEntity>>()
    val myMuments = _myMuments

    var realTagList = MutableLiveData<List<TagEntity>>(emptyList())

    val checkedTagList = MutableLiveData<List<TagEntity>>(emptyList())

    private val _isGridLayout = MutableStateFlow(false)
    val isGridLayout = _isGridLayout.asStateFlow()

    init {
        fetchMyMumentList()
    }

    fun fetchMyMumentList() {
        viewModelScope.launch {
            fetchMyMumentListUseCase().runCatching {
                _myMuments.value = this
            }
        }
    }

    fun changeIsGridLayout(isGrid: Boolean) {
        _isGridLayout.value = isGrid
    }

    fun addCheckedList(checkedId: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList() ?: mutableListOf()
        if(tempList.size <= 3) {
            tempList.add(checkedId)
            checkedTagList.value = tempList
        }
    }

    fun removeCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList() ?: mutableListOf()
        tempList.remove(tag)
        checkedTagList.value = tempList
    }

    fun resetCheckedList() {
        checkedTagList.value?.toMutableList()?.let {
            it.clear()
            checkedTagList.value = it
        }
    }
}