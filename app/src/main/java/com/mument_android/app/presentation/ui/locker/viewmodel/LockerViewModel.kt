package com.mument_android.app.presentation.ui.locker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.app.data.dto.MumentCard
import com.mument_android.app.domain.entity.LockerMumentEntity
import com.mument_android.app.domain.entity.MumentCardData.Music
import com.mument_android.app.domain.entity.MumentCardData.User
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
    private val _myMuments = MutableLiveData<List<LockerMumentEntity>>()
    val myMuments = _myMuments

    private val _checkedTagList = MutableLiveData<List<TagEntity>>(listOf())
    val checkedTagList: LiveData<List<TagEntity>> = _checkedTagList

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
        tempList.add(checkedId)
        _checkedTagList.value = tempList
    }

    fun removeCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList() ?: mutableListOf()
        tempList.remove(tag)
        _checkedTagList.value = tempList
    }

    fun resetCheckedList() {
        val tempList = checkedTagList.value?.toMutableList() ?: mutableListOf()
        tempList?.clear()
        _checkedTagList.value = tempList
    }
}