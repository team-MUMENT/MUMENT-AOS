package com.mument_android.app.presentation.ui.locker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.locker.LockerMumentEntity
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.usecase.locker.FetchMyLikeListUseCase
import com.mument_android.app.domain.usecase.locker.FetchMyMumentListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LockerViewModel @Inject constructor(
    private val fetchMyMumentListUseCase: FetchMyMumentListUseCase,
    private val fetchMyLikeListUseCase: FetchMyLikeListUseCase
) : ViewModel() {
    val emotionalTags =
        EmotionalTag.values().map { TagEntity(TagEntity.TAG_EMOTIONAL, it.tag, it.tagIndex) }
    val impressionTags =
        ImpressiveTag.values().map { TagEntity(TagEntity.TAG_IMPRESSIVE, it.tag, it.tagIndex) }

    val myMuments = MutableStateFlow<ApiResult<List<LockerMumentEntity>>?>(null)

    val myLikeMuments = MutableStateFlow<ApiResult<List<LockerMumentEntity>>?>(null)

    private val _checkedTagList = MutableLiveData<List<TagEntity>>(emptyList())
    val checkedTagList = _checkedTagList


    //좋아요한 뮤멘트 실시간 태그 리스트
    private val _checkedLikeTagList = MutableLiveData<List<TagEntity>>(emptyList())
    val checkedLikeTagList = _checkedLikeTagList

    //뮤멘트 GridLayout
    private val _isGridLayout = MutableStateFlow(false)
    var isGridLayout = _isGridLayout.asStateFlow()

    //좋아요 GridLayout
    private val _isLikeGridLayout = MutableStateFlow(false)
    val isLikeGridLayout = _isLikeGridLayout.asStateFlow()

    var firstTag: Int? = 0
    var secondTag: Int? = 0
    var thirdTag: Int? = 0


    fun changeCheckedTagList(tags: List<TagEntity>) {
        _checkedTagList.value = tags
    }

    //좋아요 한 뮤멘트
    fun changeLikeCheckedTagList(tags: List<TagEntity>) {
        _checkedLikeTagList.value = tags
    }

    fun fetchMyMumentList() {
        viewModelScope.launch {
            checkedTagList.value?.let { tags ->
                if (tags.isEmpty()) {
                    firstTag = null
                    secondTag = null
                    thirdTag = null
                } else if (tags.size == 1) {
                    firstTag = tags.get(0).tagIdx
                    secondTag = null
                    thirdTag = null
                } else if (tags.size == 2) {
                    firstTag = tags.get(0).tagIdx
                    secondTag = tags.get(1).tagIdx
                    thirdTag = null
                } else if (tags.size == 3) {
                    firstTag = tags.get(0).tagIdx
                    secondTag = tags.get(1).tagIdx
                    thirdTag = tags.get(2).tagIdx
                } else {
                    firstTag = null
                    secondTag = null
                    thirdTag = null
                }
            }

            fetchMyMumentListUseCase(
                userId = "62cd5d4383956edb45d7d0ef",
                tag1 = firstTag,
                tag2 = secondTag,
                tag3 = thirdTag
            ).onStart {
                myMuments.value = ApiResult.Loading(null)
            }.catch {
                it.printStackTrace()
                myMuments.value = ApiResult.Failure(null)
            }.collect {
                myMuments.value = ApiResult.Success(it)
            }
        }
    }

    fun fetchMyLikeList() {
        viewModelScope.launch {
            checkedLikeTagList.value?.let { tags ->
                if (tags.isEmpty()) {
                    firstTag = null
                    secondTag = null
                    thirdTag = null
                } else if (tags.size == 1) {
                    firstTag = tags.get(0).tagIdx
                    secondTag = null
                    thirdTag = null
                } else if (tags.size == 2) {
                    firstTag = tags.get(0).tagIdx
                    secondTag = tags.get(1).tagIdx
                    thirdTag = null
                } else if (tags.size == 3) {
                    firstTag = tags.get(0).tagIdx
                    secondTag = tags.get(1).tagIdx
                    thirdTag = tags.get(2).tagIdx
                } else {
                    firstTag = null
                    secondTag = null
                    thirdTag = null
                }
            }

            fetchMyLikeListUseCase(
                userId = "62cd5d4383956edb45d7d0ef",
                tag1 = firstTag,
                tag2 = secondTag,
                tag3 = thirdTag
            ).onStart {
                myLikeMuments.value = ApiResult.Loading(null)
            }.catch {
                it.printStackTrace()
                myLikeMuments.value = ApiResult.Failure(null)
            }.collect {
                myLikeMuments.value = ApiResult.Success(it)
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


    fun removeCheckedList(tag: TagEntity) {
        val tempList = checkedTagList.value?.toMutableList() ?: mutableListOf()
        tempList.remove(tag)
        checkedTagList.value = tempList
    }


    fun removeLikeCheckedList(tag: TagEntity) {
        val tempList = checkedLikeTagList.value?.toMutableList() ?: mutableListOf()
        tempList.remove(tag)
        checkedLikeTagList.value = tempList
    }

}