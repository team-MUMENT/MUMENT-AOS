package com.mument_android.login

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.entity.sign.WebViewEntity
import com.mument_android.domain.usecase.sign.GetWebViewUseCase
import com.mument_android.domain.usecase.sign.GetWebViewUseCaseImpl
import com.mument_android.domain.usecase.sign.SignDulCheckUseCase
import com.mument_android.domain.usecase.sign.SignPutProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val dupCheckUseCase : SignDulCheckUseCase,
    private val putProfileUseCase: SignPutProfileUseCase,
    private val getWebViewUseCase: GetWebViewUseCase
): ViewModel() {

    val mumentNickName = MutableLiveData<String>()
    val isRightPattern = MutableLiveData<Boolean>()
    val isActive = MutableLiveData<Boolean>()
    val imageUri = MutableLiveData<Uri?>(null)

    val isDuplicate = MutableLiveData<Int>(null)
    val image = MutableLiveData<String>(null)

    private val _putProfile = MutableLiveData<SetProfileEntity>()
    val putProfile get() :LiveData<SetProfileEntity> = _putProfile

    private val _getWebView = MutableLiveData<WebViewEntity>()
    val getWebView get() :LiveData<WebViewEntity> = _getWebView

    fun nickNameDupCheck(nickname: String) {
        viewModelScope.launch {
            isDuplicate.value = dupCheckUseCase.dupCheckNickname(nickname)
        }
    }

    fun putProfile(image: MultipartBody.Part?, body: HashMap<String, RequestBody>) {
        viewModelScope.launch {
            kotlin.runCatching {
                putProfileUseCase(image, body).let {
                    _putProfile.value = it
                }
            }
        }
    }

    fun getWebView(page: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getWebViewUseCase.getWebView(page).let {
                    _getWebView.value = it
                }
            }
        }
    }
}