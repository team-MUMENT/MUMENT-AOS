package com.mument_android.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.mypage.data.NoticeData
import com.mument_android.mypage.data.UserData

class MyPageViewModel : ViewModel() {

    //myPage
    private val _isBtnClick = MutableLiveData(false)
    val isBtnClick = _isBtnClick

    //Profile
    val userId = MutableLiveData<String>()
    val userImg = MutableLiveData<Int>()
    val userNickNameContent = MutableLiveData("")

    //Notice
    private val _noticeList = MutableLiveData<List<NoticeData>>()
    val noticeList = _noticeList

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


}


