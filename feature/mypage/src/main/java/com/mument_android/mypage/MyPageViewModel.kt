package com.mument_android.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiResult
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.domain.entity.mypage.NoticeListEntity
import com.mument_android.domain.usecase.mypage.DeleteBlockUserUseCase
import com.mument_android.domain.usecase.mypage.FetchBlockUserUseCase
import com.mument_android.domain.usecase.mypage.FetchNoticeDetailUseCase
import com.mument_android.domain.usecase.mypage.FetchNoticeListUseCase
import com.mument_android.mypage.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val fetchBlockUserUseCase: FetchBlockUserUseCase,
    private val deleteBlockUserUseCase: DeleteBlockUserUseCase,
    private val fetchNoticeListUseCase: FetchNoticeListUseCase,
    private val fetchNoticeDetailUseCase: FetchNoticeDetailUseCase
) : ViewModel() {


    //myPage
    private val _isBtnClick = MutableLiveData(false)
    val isBtnClick: LiveData<Boolean> get() = _isBtnClick

    //Profile
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _userImg = MutableLiveData<String>()
    val userImg: LiveData<String> get() = _userImg

    private val _userNickNameContent = MutableLiveData("")
    val userNickNameContent = _userNickNameContent

    //BlockUserManagement
    private val _blockUserList = MutableStateFlow<ApiResult<List<BlockUserEntity>>?>(null)
    val blockUserList get() = _blockUserList.asStateFlow()

    //Notice
    private val _noticeList = MutableStateFlow<ApiResult<List<NoticeListEntity>>?>(null)
    val noticeList get() = _noticeList.asStateFlow()

    private val _noticeDetail = MutableStateFlow<ApiResult<NoticeListEntity>?>(null)
    val noticeDetail get() = _noticeDetail.asStateFlow()

    private val _noticeId = MutableLiveData<Int>()
    val noticeId = _noticeId

    //Unregister
    private val _isClickReasonChooseBox = MutableLiveData(false)
    val isClickReasonChooseBox: LiveData<Boolean> get() = _isClickReasonChooseBox

    private val _isClickReasonChoose = MutableLiveData(false)
    val isClickReasonChoose: LiveData<Boolean> get() = _isClickReasonChoose

    private val _isSelectSixthReason = MutableLiveData(false)
    val isSelectSixthReason: LiveData<Boolean> get() = _isSelectSixthReason

    private val _unregisterReasonContent = MutableLiveData("")
    val unregisterReasonContent = _unregisterReasonContent

    private val _isUnregisterAgree = MutableLiveData(false)
    val isUnregisterAgree: LiveData<Boolean> get() = _isUnregisterAgree

    //마이페이지 뷰이동 버튼 클릭
    fun isClickBtnEvent(isBtnClick: Boolean) {
        _isBtnClick.value = isBtnClick
    }

    //프로필 유저 정보
    fun fetchUserInfo() {
        val userData = UserData(
            userID = userId.value ?: "",
            userImg = userImg.value ?: ""
        )
    }

    //차단유저관리
    fun fetchBlockUserList() {
        viewModelScope.launch {
            blockUserList.value.let {
                fetchBlockUserUseCase.invoke().onStart {
                }.catch { _blockUserList.value = ApiResult.Failure(null) }
                    .collect {
                        _blockUserList.value = ApiResult.Success(it)
                    }
            }
        }
    }

    //차단 유저 해제
    fun deleteBlockUser(userId: Int) {
        viewModelScope.launch {
            deleteBlockUserUseCase(blockedUserId = userId)
                .catch { }
                .collect {
                    fetchBlockUserList()
                }
        }
    }


    //공지사항
    fun fetchNoticeList() {
        viewModelScope.launch {
            noticeList.value.let {
                fetchNoticeListUseCase.invoke().onStart {
                }.catch {
                    _noticeList.value = ApiResult.Failure(null)
                }.collect {
                    _noticeList.value = ApiResult.Success(it)
                }
            }
        }
    }

    fun fetchNoticeDetail(noticeId: Int) {
        viewModelScope.launch {
            noticeDetail.value.let {
                fetchNoticeDetailUseCase.invoke(noticeId).onStart {
                }.catch {
                    _noticeDetail.value = ApiResult.Failure(null)
                }.collect {
                    _noticeDetail.value = ApiResult.Success(it)
                }
            }
        }
    }

    //이유 선택 박스 눌렀을 때 라디오 그룹 visibility 조절
    fun clickReasonChooseBox() {
        _isClickReasonChooseBox.value = !(_isClickReasonChooseBox.value!!)
    }

    //이유 선택 하기
    fun clickReasonChoose() {
        _isClickReasonChoose.value = true
    }

    //이유 중 '기타' 를 눌렀을 때 boolean 값
    fun clickSixthReason(checkedID: Boolean) {
        _isSelectSixthReason.value = checkedID
    }

    //탈퇴 동의
    fun clickUnregisterAgree() {
        _isUnregisterAgree.value = !(_isUnregisterAgree.value!!)
    }

    //이유선택박스 초기화
    fun initReasonChooseBox() {
        _isClickReasonChooseBox.value = false
    }
}


