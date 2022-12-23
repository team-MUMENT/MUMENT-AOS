package com.mument_android.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.mypage.data.NoticeData
import com.mument_android.mypage.data.UserData

class MyPageViewModel : ViewModel() {

    //myPage
    private val _isBtnClick = MutableLiveData(false)
    val isBtnClick: LiveData<Boolean> get() = _isBtnClick

    //Profile
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId
    val userImg = MutableLiveData<Int>()
    val userNickNameContent = MutableLiveData("")

    //Notice
    private val _noticeList = MutableLiveData<List<NoticeData>>()
    val noticeList: LiveData<List<NoticeData>> get() = _noticeList

    //Unregister
    private val _isClickReasonChooseBox = MutableLiveData(false)
    val isClickReasonChooseBox: LiveData<Boolean> get() = _isClickReasonChooseBox

    private val _isClickReasonChoose = MutableLiveData(false)
    val isClickReasonChoose: LiveData<Boolean> get() = _isClickReasonChoose

    private val _isSelectSixthReason = MutableLiveData(false)
    val isSelectSixthReason = _isSelectSixthReason

    private val _unregisterReasonContent = MutableLiveData("")
    val unregisterReasonContent = _unregisterReasonContent

    private val _isUnregisterAgree = MutableLiveData(false)
    val isUnregisterAgree =_isUnregisterAgree

    //마이페이지 뷰이동 버튼 클릭
    fun isClickBtnEvent(isBtnClick: Boolean) {
        _isBtnClick.value = isBtnClick
    }

    //프로필 유저 정보
    fun fetchUserInfo() {
        val userData = UserData(
            userID = userId.value ?: "",
            userImg = userImg.value ?: 0
        )
    }

    //이유선택 박스 눌렀을 때
    fun clickReasonChooseBox() {
        _isClickReasonChooseBox.value = _isClickReasonChooseBox.value != true
    }

    fun clickReasonChoose() {
        _isClickReasonChoose.value = true
    }

    fun clickUnregisterAgree(){
        _isUnregisterAgree.value = _isUnregisterAgree.value != true

    }
}


