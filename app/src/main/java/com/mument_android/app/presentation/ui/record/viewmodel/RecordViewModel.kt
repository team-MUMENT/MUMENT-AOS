package com.mument_android.app.presentation.ui.record.viewmodel
import androidx.lifecycle.*
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.usecase.record.IsFirstRecordMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(val useCase: IsFirstRecordMumentUseCase) : ViewModel() {
    private val _checkedTagList = MutableLiveData<List<TagEntity>>(listOf())
    val checkedTagList get():LiveData<List<TagEntity>> = _checkedTagList

    private val _isFirst = MutableLiveData<Boolean>(true)
    val isFirst get() :LiveData<Boolean> = _isFirst
    val text = MutableLiveData<String>()


    private val _isSelectedMusic = MutableLiveData<Boolean>()
    val isSelectedMusic get(): LiveData<Boolean> = _isSelectedMusic

    private val _selectedMusic = MutableLiveData<RecentSearchData>()
    val selectedMusic = _selectedMusic

//    val data = SearchResultData("25", "불꽃카리스마", "이민호","https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize",true)

    fun findIsFirst() {
        viewModelScope.launch {
            useCase.invoke( "62cd5d4383956edb45d7d0ef","62cd4416177f6e81ee8fa398").onStart {

            }.collect {
                Timber.d("Collect!!! $it")
                _isFirst.value = it.isFirst
            }
        }
    }

    fun changeSelectedMusic(music: RecentSearchData) {
        _selectedMusic.value = music
    }


    fun addCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList() ?: mutableListOf()
        if (tempList.size <= 5) {
            tempList.add(tag)
            _checkedTagList.value = tempList
        }

    }

    fun removeCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList() ?: mutableListOf()
        tempList.remove(tag)
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

    fun checkSelectedMusic(isSelected: Boolean) {
        _isSelectedMusic.value = isSelected
        findIsFirst()
    }

    fun removeSelectedMusic() {
        selectedMusic.value = null
        _isSelectedMusic.value = false
    }

}