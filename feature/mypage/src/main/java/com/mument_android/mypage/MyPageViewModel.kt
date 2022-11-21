package com.mument_android.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.mypage.data.UserData

class MyPageViewModel : ViewModel() {

    private val _isBtnClick = MutableLiveData(false)
    val isBtnClick = _isBtnClick

    val userId = MutableLiveData<String>()
    val userImg = MutableLiveData<Int>()


    fun isClickBtnEvent(isBtnClick: Boolean) {
        _isBtnClick.value = isBtnClick
    }

    fun fetchUserInfo() {
        val userData = UserData(
            userID = userId.value ?: "",
            userImg = userImg.value ?: 0
        )

    }
}


