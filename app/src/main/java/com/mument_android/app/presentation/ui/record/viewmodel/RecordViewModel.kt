package com.mument_android.app.presentation.ui.record.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.TagEntity


class RecordViewModel : ViewModel() {
    private val _checkedTagList = MutableLiveData<List<TagEntity>>(listOf())
    val checkedTagList: LiveData<List<TagEntity>> = _checkedTagList

    private val _isFirst = MutableLiveData<Boolean>()
    val isFirst = _isFirst

    val text = MutableLiveData<String>()

    fun addCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList()
        tempList?.add(tag)
        _checkedTagList.value = tempList
    }

    fun removeCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList()
        tempList?.remove(tag)
        _checkedTagList.value = tempList
    }

    fun checkIsFirst(isFirst: Boolean) {
        _isFirst.value = isFirst
    }
}