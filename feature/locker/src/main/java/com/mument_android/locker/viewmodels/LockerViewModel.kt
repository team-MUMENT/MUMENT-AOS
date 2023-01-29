package com.mument_android.locker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.model.TagEntity
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.domain.usecase.locker.FetchMyLikeListUseCase
import com.mument_android.domain.usecase.locker.FetchMyMumentListUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import com.mument_android.core.network.ApiResult
import com.mument_android.locker.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockerViewModel @Inject constructor(
    private val fetchMyMumentListUseCase: FetchMyMumentListUseCase,
    private val fetchMyLikeListUseCase: FetchMyLikeListUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase,
    private val likeMumentUseCase: LikeMumentUseCase
) : ViewModel() {
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
                tag1 = firstTag,
                tag2 = secondTag,
                tag3 = thirdTag
            ).onStart {
                myMuments.value = ApiResult.Loading(null)
            }.catch {
                //Todo exception handling
                myMuments.value = ApiResult.Failure(null)
            }.collectLatest {
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
                tag1 = firstTag,
                tag2 = secondTag,
                tag3 = thirdTag
            ).onStart {
                myLikeMuments.value = ApiResult.Loading(null)
            }.catch {
                //Todo exception handling
                myLikeMuments.value = ApiResult.Failure(null)
            }.collectLatest {
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

    fun cancelLikeMument(mumentId: String) {
        viewModelScope.launch {
            cancelLikeMumentUseCase(
                mumentId,
                BuildConfig.USER_ID
            ).collect()
        }
    }

    fun likeMument(mumentId: String) {
        viewModelScope.launch {
            likeMumentUseCase(
                mumentId,
                BuildConfig.USER_ID
            ).collect()
        }
    }

}