package com.mument_android.app.presentation.ui.record.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class RecordViewModel :ViewModel(){
    private val _checkedTagList = MutableLiveData<List<Int>>(listOf(1,2))
    val checkedTagList: LiveData<List<Int>> = _checkedTagList

    private val _countText = MutableLiveData<String>()
    val countText : LiveData<String> = _countText

    private val _isFirst = MutableLiveData<Boolean>()
    val isFirst = _isFirst

    val text  = MutableLiveData<String>()

    fun addCheckedList(checkedId: Int) {
        val tempList = checkedTagList.value?.toMutableList()
        tempList?.add(checkedId)
        _checkedTagList.value = tempList
    }

    fun checkIsFirst(isFirst: Boolean) {
        _isFirst.value = isFirst
    }



//    private val _clickEvent = MutableLiveData<Boolean>()
//    val clickEvent= _clickEvent
//
//
//    fun btnClickEvent(){
//        _clickEvent.value = true
//        _clickEvent.value != _clickEvent.value
//    }
}