package com.mument_android.locker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.model.TagEntity
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.domain.entity.mypage.UserInfoEntity
import com.mument_android.domain.usecase.locker.FetchMyLikeListUseCase
import com.mument_android.domain.usecase.locker.FetchMyMumentListUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import com.mument_android.domain.usecase.mypage.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockerViewModel @Inject constructor(
    private val fetchMyMumentListUseCase: FetchMyMumentListUseCase,
    private val fetchMyLikeListUseCase: FetchMyLikeListUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val userInfoUseCase: UserInfoUseCase
) : ViewModel() {
    val myMuments = MutableStateFlow<List<LockerMumentEntity>?>(null)

    val myLikeMuments = MutableStateFlow<List<LockerMumentEntity>?>(null)

    private val _checkedTagList = MutableLiveData<List<TagEntity>>(emptyList())
    val checkedTagList = _checkedTagList

    private val _profileImage = MutableLiveData<String?>(null)
    val profileImage = _profileImage

    //userInfo
    private val _userInfo = MutableLiveData<UserInfoEntity>()
    val userInfo get(): LiveData<UserInfoEntity> = _userInfo

    //좋아요한 뮤멘트 실시간 태그 리스트
    private val _checkedLikeTagList = MutableLiveData<List<TagEntity>>(emptyList())
    val checkedLikeTagList = _checkedLikeTagList

    //뮤멘트 GridLayout
    private val _isGridLayout = MutableStateFlow(false)
    var isGridLayout = _isGridLayout.asStateFlow()

    //좋아요 GridLayout
    private val _isLikeGridLayout = MutableStateFlow(false)
    val isLikeGridLayout = _isLikeGridLayout.asStateFlow()


    var isMument: Boolean = true
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
            _checkedTagList.value?.let { tags ->
                firstTag = tags.getOrNull(0)?.tagIdx
                secondTag = tags.getOrNull(1)?.tagIdx
                thirdTag = tags.getOrNull(2)?.tagIdx
            }
            fetchMyMumentListUseCase(
                tag1 = firstTag,
                tag2 = secondTag,
                tag3 = thirdTag
            ).catch {
                //Todo exception handling
            }.collect {
                myMuments.value = it
                //_profileImage.value = it.get(0).mumentCard?.get(0)?.userImage ?: ""
            }
        }
    }

    fun fetchMyLikeList() {
        viewModelScope.launch {
            _checkedLikeTagList.value?.let { tags ->
                firstTag = tags.getOrNull(0)?.tagIdx
                secondTag = tags.getOrNull(1)?.tagIdx
                thirdTag = tags.getOrNull(2)?.tagIdx
            }
            fetchMyLikeListUseCase(
                tag1 = firstTag,
                tag2 = secondTag,
                tag3 = thirdTag
            ).catch {
                //Todo exception handling
            }.collect {
                myLikeMuments.value = it.map { entity ->
                    entity.copy(isOther = true)
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
                mumentId
            ).collect()
        }
    }

    fun likeMument(mumentId: String) {
        viewModelScope.launch {
            likeMumentUseCase(
                mumentId
            ).collect()
        }
    }

    //유저 정보
    fun userInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                userInfoUseCase.invoke().let {
                    _userInfo.value = it
                }
            }
        }
    }

}