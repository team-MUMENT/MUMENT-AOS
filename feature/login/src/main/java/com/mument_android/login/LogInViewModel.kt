package com.mument_android.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiResult
import com.mument_android.domain.usecase.sign.SignDulCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val dupCheckUseCase : SignDulCheckUseCase
): ViewModel() {

    val mumentNickName = MutableLiveData<String>()
    val isRightPattern = MutableLiveData<Boolean>()
    val isActive = MutableLiveData<Boolean>()
    val imageUri = MutableLiveData<Uri?>()

    val isDuplicate = MutableStateFlow<ApiResult<Any>?>(null)


    fun nickNameDupCheck(nickname: String) {
        viewModelScope.launch {
            dupCheckUseCase.invoke(nickname)
        }
    }
}