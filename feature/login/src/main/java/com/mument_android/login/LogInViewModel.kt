package com.mument_android.login

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(): ViewModel() {

    val mumentNickName = MutableLiveData<String>()
    val isRightPattern = MutableLiveData<Boolean>()
    val isActive = MutableLiveData<Boolean>()
    val imageUri = MutableLiveData<Uri?>()
}