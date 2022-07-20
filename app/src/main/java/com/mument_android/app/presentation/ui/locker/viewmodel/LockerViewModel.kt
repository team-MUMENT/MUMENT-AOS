package com.mument_android.app.presentation.ui.locker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.locker.LockerMumentEntity
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.usecase.locker.FetchMyMumentListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LockerViewModel @Inject constructor(
    private val fetchMyMumentListUseCase: FetchMyMumentListUseCase
): ViewModel() {
    val emotionalTags = EmotionalTag.values().map { TagEntity(TagEntity.TAG_EMOTIONAL, it.tag, it.tagIndex) }
    val impressionTags = ImpressiveTag.values().map { TagEntity(TagEntity.TAG_IMPRESSIVE, it.tag, it.tagIndex) }

    val myMuments = MutableStateFlow<ApiResult<List<LockerMumentEntity>>?>(null)

    var realTagList = MutableLiveData<List<TagEntity>>(emptyList())
    val checkedTagList = MutableLiveData<List<TagEntity>>(emptyList())


    //좋아요한 뮤멘트 완료 버튼 누른 뒤 나오는 리스트
    var likeRealTagList = MutableLiveData<List<TagEntity>>(emptyList())

    //좋아요한 뮤멘트 실시간 처리하는 리스트
    var checkedLikeTagList = MutableLiveData<List<TagEntity>>(emptyList())

    private val _isGridLayout = MutableStateFlow(false)
    val isGridLayout = _isGridLayout.asStateFlow()

    //좋아요 GridLayout
    private val _isLikeGridLayout = MutableStateFlow(false)
    val isLikeGridLayout = _isLikeGridLayout.asStateFlow()

    init {
        fetchMyMumentList()
    }

    fun fetchMyMumentList() {
        viewModelScope.launch {
            fetchMyMumentListUseCase(userId = "62cd5d4383956edb45d7d0ef", tag1 = null, tag2 = null, tag3 = null).runCatching {
                this.onStart {
                    myMuments.value = ApiResult.Loading(null)
                }.catch {
                    it.printStackTrace()
                    myMuments.value = ApiResult.Failure(null)
                }.collect {
                    myMuments.value = ApiResult.Success(it)
                }
            }
        }
    }

    fun changeIsGridLayout(isGrid: Boolean) {
        _isGridLayout.value = isGrid
    }

    //좋아요 그리드 변경
    fun changeLikeIsGridLayout(isGrid: Boolean) {
        _isLikeGridLayout.value = isGrid
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

    fun addLikeCheckedList(checkedId: TagEntity) {
        val tempList = checkedLikeTagList.value?.toMutableList() ?: mutableListOf()
        if(tempList.size <= 3) {
            tempList.add(checkedId)
            checkedLikeTagList.value = tempList
        }
    }

    fun removeLikeCheckedList(tag: TagEntity) {
        val tempList = checkedLikeTagList.value?.toMutableList() ?: mutableListOf()
        tempList.remove(tag)
        checkedLikeTagList.value = tempList
    }

    fun resetLikeCheckedList() {
        checkedLikeTagList.value?.toMutableList()?.let {
            it.clear()
            checkedLikeTagList.value = it
        }
    }
}