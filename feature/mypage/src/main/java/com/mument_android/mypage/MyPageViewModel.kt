package com.mument_android.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.mypage.data.UserData

class MyPageViewModel : ViewModel() {

    private val _isBtnClick = MutableLiveData(false)
    val isBtnClick = _isBtnClick

    fun isClickBtnEvent(isBtnClick: Boolean) {
        _isBtnClick.value = isBtnClick
    }


}