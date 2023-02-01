package com.mument_android.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiResult
import com.mument_android.domain.entity.sign.SetProfileData
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.usecase.sign.SignDulCheckUseCase
import com.mument_android.domain.usecase.sign.SignPutProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val dupCheckUseCase : SignDulCheckUseCase,
    private val putProfileUseCase: SignPutProfileUseCase
): ViewModel() {

    val mumentNickName = MutableLiveData<String>()
    val isRightPattern = MutableLiveData<Boolean>()
    val isActive = MutableLiveData<Boolean>()
    val imageUri = MutableLiveData<Uri?>(null)

    val isDuplicate = MutableLiveData<Int>(null)
    val image = MutableLiveData<String>(null)

    private val _putProfile = MutableLiveData<SetProfileEntity>()
    val putProfile get() :LiveData<SetProfileEntity> = _putProfile

    fun nickNameDupCheck(nickname: String) {
        viewModelScope.launch {
            dupCheckUseCase.dupCheckNickname(nickname).let {
                isDuplicate.value = it
            }
        }
    }

    fun putProfile(image: MultipartBody.Part?, body: HashMap<String, RequestBody>) {
        viewModelScope.launch {
            kotlin.runCatching {
                putProfileUseCase(image, body).let {
                    _putProfile.value = it
                    Log.e("제발", "떠라")
                }
            }
        }
    }
}