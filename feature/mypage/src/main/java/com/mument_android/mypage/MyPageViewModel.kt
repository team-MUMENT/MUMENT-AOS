package com.mument_android.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.domain.usecase.mypage.FetchBlockUserUseCase
import com.mument_android.mypage.data.NoticeData
import com.mument_android.mypage.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val fetchBlockUserUseCase: FetchBlockUserUseCase
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
    private val _blockUserList = MutableLiveData<List<BlockUserEntity>>()
    val blockUserList: LiveData<List<BlockUserEntity>> get() = _blockUserList

    //Notice
    private val _noticeList = MutableLiveData<List<NoticeData>>()
    val noticeList: LiveData<List<NoticeData>> get() = _noticeList

    val noticeId = MutableLiveData<Int>()
    val noticeTitle = MutableLiveData<String>()
    val noticeDate = MutableLiveData<String>()
    val noticeContent = MutableLiveData<String>()

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
//    fun unblockUser(element: UserData) {
//        val currentList = blockUserList.value!!.toMutableList()
//        currentList.remove(element)
//        _blockUserList.value = currentList
//    }

    fun fetchBlockUserList() {
        viewModelScope.launch {
            blockUserList.value.let {
                fetchBlockUserUseCase.invoke().onStart {
                }.collect {
                    _blockUserList.value = it
                }
            }
        }
    }


    //공지사항
    fun fetchNoticeDetail() {
        val noticeData = NoticeData(
            id = noticeId.value ?: 0,
            title = noticeTitle.value.orEmpty(),
            created_at = noticeDate.value.orEmpty(),
            content = noticeContent.value.orEmpty(),
        )
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


