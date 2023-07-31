package com.mument_android.record.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.util.EmotionalTag
import com.mument_android.core_dependent.util.ImpressiveTag
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.entity.record.MumentModifyEntity
import com.mument_android.domain.entity.record.MumentRecordEntity
import com.mument_android.domain.usecase.record.IsFirstRecordMumentUseCase
import com.mument_android.domain.usecase.record.RecordModifyMumentUseCase
import com.mument_android.domain.usecase.record.RecordMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    var isFirstDuplicate = false

    val mumentId = MutableLiveData<String?>("")
    val mumentContent = MutableLiveData<String>()

    private val _isSelectedMusic = MutableLiveData<Boolean>(false)
    val isSelectedMusic get(): LiveData<Boolean> = _isSelectedMusic

    private val _selectedMusic = MutableLiveData<RecentSearchData>()
    val selectedMusic = _selectedMusic

    private val _createdMumentId = MutableLiveData<String>()
    val createdMumentId = _createdMumentId

    private val _modifyMumentId = MutableLiveData<String?>(null)
    val modifyMumentId = _modifyMumentId

    val mumentData = MutableLiveData<MumentDetailEntity?>()

    var isPrivate = MutableLiveData<Boolean>(false)

    private val _isModifySuccessful = MutableSharedFlow<Boolean>()
    val isModifySuccessful = _isModifySuccessful.asSharedFlow()

    private val _isCreateSuccessful = MutableSharedFlow<Boolean>()
    val isCreateSuccessful = _isCreateSuccessful.asSharedFlow()

    private val _isRecord = MutableLiveData<Boolean>()
    val isRecord = _isRecord

    var recordCount = 0

    fun findIsFirst() {
        viewModelScope.launch {
            selectedMusic.value?.let {
                checkIsFirstRecordUseCase.invoke(it._id).onStart {
                }.collect {
                    _isFirst.value = it.isFirst
                }
            }
        }
    }

    fun setIntentData(mumentModifyEntity: MumentModifyEntity, muId: String) {
        mumentId.value = muId
        _modifyMumentId.value = muId
        if (mumentModifyEntity.content == "null") mumentContent.value = ""
        else mumentContent.value = mumentModifyEntity.content
        _isFirst.value = mumentModifyEntity.isFirst
        isPrivate.value = mumentModifyEntity.isPrivate
        mumentData.value = null
        _checkedTagList.value =
            (mumentModifyEntity.impressionTag + mumentModifyEntity.feelingTag).map { tag ->
                if (tag < 200) TagEntity(
                    TagEntity.TAG_IMPRESSIVE,
                    ImpressiveTag.findImpressiveStringTag(tag),
                    tag
                )
                else TagEntity(
                    TagEntity.TAG_EMOTIONAL,
                    EmotionalTag.findEmotionalStringTag(tag),
                    tag
                )
            }
    }

    fun postMument() {
        viewModelScope.launch {
            checkedTagList.value?.let { tags ->
                val feelingTags = tags.filter { it.tagIdx >= 200 }.map { it.tagIdx }
                val impressionTags = tags.filter { it.tagIdx < 200 }.map { it.tagIdx }
                val recordEntity = MumentRecordEntity(
                    content = mumentContent.value ?: "",
                    feelingTags,
                    impressionTags,
                    isFirstDuplicate,
                    isPrivate.value ?: false,
                    selectedMusic.value!!._id,
                    selectedMusic.value!!.artist,
                    selectedMusic.value!!.image,
                    selectedMusic.value!!.name
                )
                selectedMusic.value?.let { recent ->
                    recordMumentUseCase(
                        musicId = recent._id,
                        recordEntity
                    ).catch { e ->
                        _isCreateSuccessful.emit(false)
                    }.collect { pair ->
                        _createdMumentId.value = pair.first
                        recordCount = pair.second
                        _isCreateSuccessful.emit(true)
                    }
                }
            }
        }
    }

    fun modifyMument() {
        viewModelScope.launch {
            checkedTagList.value?.let { tags ->
                val feelingTags = tags.filter { it.tagIdx >= 200 }.map { it.tagIdx }
                val impressionTags = tags.filter { it.tagIdx < 200 }.map { it.tagIdx }
                val modifyEntity = MumentModifyEntity(
                    content = mumentContent.value ?: "",
                    feelingTags,
                    impressionTags,
                    isFirstDuplicate,
                    isPrivate.value ?: false,
                )
                recordModifyMumentUseCase(
                    mumentId = _modifyMumentId.value!!,
                    modifyEntity
                ).catch { e ->
                    _isModifySuccessful.emit(false)
                }.collect {
                    _modifyMumentId.value = it
                    _isModifySuccessful.emit(true)
                }
            }
        }
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

}