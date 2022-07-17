package com.mument_android.app.presentation.ui.record.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.SearchResultData
import com.mument_android.app.domain.entity.TagEntity


class RecordViewModel : ViewModel() {
    private val _checkedTagList = MutableLiveData<List<TagEntity>>(listOf())
    val checkedTagList get():LiveData<List<TagEntity>> = _checkedTagList

    private val _isFirst = MutableLiveData<Boolean>()
    val isFirst get() :LiveData<Boolean> = _isFirst
    val text = MutableLiveData<String>()

    private val _isSelectedMusic = MutableLiveData<Boolean>()
    val isSelectedMusic get(): LiveData<Boolean> = _isSelectedMusic

    val data = SearchResultData("25", "불꽃카리스마", "이민호","https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",true)

    fun addCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList() ?: mutableListOf()
        if (tempList.size <= 5) {
            tempList.add(tag)
            _checkedTagList.value = tempList
        }

    }

    fun removeCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList()
        tempList?.remove(tag)
        _checkedTagList.value = tempList
    }

    fun resetCheckedList() {
        checkedTagList.value?.toMutableList()?.let {
            it.clear()
            _checkedTagList.value = it
        }
    }

    fun checkIsFirst(isFirst: Boolean) {
        _isFirst.value = isFirst
    }

    fun checkSelectedMusic(isSelected : Boolean) {
        _isSelectedMusic.value = isSelected
    }

}