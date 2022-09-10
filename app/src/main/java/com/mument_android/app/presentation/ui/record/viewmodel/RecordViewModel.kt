package com.mument_android.app.presentation.ui.record.viewmodel

import androidx.lifecycle.*
import com.mument_android.BuildConfig
import com.mument_android.app.data.dto.record.MumentRecordDto
import com.mument_android.app.data.local.recentlist.RecentSearchData
//TODO data layer remove
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.usecase.record.IsFirstRecordMumentUseCase
import com.mument_android.app.domain.usecase.record.RecordModifyMumentUseCase
import com.mument_android.app.domain.usecase.record.RecordMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val checkIsFirstRecordUseCase: IsFirstRecordMumentUseCase,
    private val recordMumentUseCase: RecordMumentUseCase,
    private val recordModifyMumentUseCase: RecordModifyMumentUseCase
) : ViewModel() {
    private val _checkedTagList = MutableLiveData<List<TagEntity>>(listOf())
    val checkedTagList get():LiveData<List<TagEntity>> = _checkedTagList

    private val _isFirst = MutableLiveData<Boolean?>()
    val isFirst: LiveData<Boolean?> get() :LiveData<Boolean?> = _isFirst

    val mumentId  = MutableLiveData<String>("")
    val mumentContent = MutableLiveData<String>()

    private val _isSelectedMusic = MutableLiveData<Boolean>(false)
    val isSelectedMusic get(): LiveData<Boolean> = _isSelectedMusic

    private val _selectedMusic = MutableLiveData<RecentSearchData>()
    val selectedMusic = _selectedMusic

    private val _createdMumentId = MutableLiveData<String>()
    val createdMumentId = _createdMumentId

    private val _modifyMumentId = MutableLiveData<String>()
    val modifyMumentId = _modifyMumentId

    private val _isRecord = MutableLiveData<Boolean>(false)
    val isRecord get() :LiveData<Boolean> = _isRecord

    val mumentData = MutableLiveData<MumentDetailEntity>()

    var isPrivate = MutableLiveData<Boolean>(false)

    fun findIsFirst() {
        viewModelScope.launch {
            selectedMusic.value?.let {
                checkIsFirstRecordUseCase.invoke(BuildConfig.USER_ID, it._id).onStart {
                }.collect {
                    _isFirst.value = it.isFirst
                }
            }
        }
    }

    fun changeIsFirst(isFirst:Boolean){
        _isFirst.value = isFirst
    }

    fun postMument() {
        viewModelScope.launch {
            checkedTagList.value?.let { tags ->
                val feelingTags = tags.filter { it.tagIdx >= 200 }.map { it.tagIdx }
                val impressionTags = tags.filter { it.tagIdx < 200 }.map { it.tagIdx }
                val recordDto = MumentRecordDto(content = mumentContent.value ?: "", feelingTags, impressionTags, isFirst.value ?: true, isPrivate.value ?: false)
                selectedMusic.value?.let {
                    recordMumentUseCase(musicId = it._id, userId = BuildConfig.USER_ID, recordDto).catch { e ->
                        //Todo exception handling
                    }.collect {
                        _createdMumentId.value = it
                    }
                }
            }
        }
    }

    fun putMument(){
        viewModelScope.launch {
            checkedTagList.value?.let { tags ->
                val feelingTags = tags.filter { it.tagIdx >= 200 }.map { it.tagIdx }
                val impressionTags = tags.filter { it.tagIdx < 200 }.map { it.tagIdx }
                val recordDto = MumentRecordDto(content = mumentContent.value ?: "", feelingTags, impressionTags, isFirst.value ?: true, isPrivate.value ?: false)
                recordModifyMumentUseCase(mumentId = mumentId.value!!, recordDto).catch { e ->
                    //Todo exception handling
                }.collect {
                    _modifyMumentId.value = it
                }
            }
        }
    }
    fun setCheckTaglist(tagList:List<TagEntity>){
        val data = checkedTagList.value?.toMutableList()
        data?.addAll(tagList)
        _checkedTagList.value = data
    }

    fun changeSelectedMusic(music: RecentSearchData) {
        _selectedMusic.value = music
    }
    fun checkSelectedMusic(isSelected: Boolean) {
        _isSelectedMusic.value = isSelected
    }

    fun removeSelectedMusic() {
        selectedMusic.value = null
        _isSelectedMusic.value = false
        _isFirst.value = null
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
}