package com.mument_android.login

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.RequestKakaoData
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.usecase.sign.SignDulCheckUseCase
import com.mument_android.domain.usecase.sign.SignKaKaoUseCase
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
    private val kaKaoUseCase: SignKaKaoUseCase
): ViewModel() {

    val mumentNickName = MutableLiveData<String>()
    val isRightPattern = MutableLiveData<Boolean>()
    val isActive = MutableLiveData<Boolean>()
    val imageUri = MutableLiveData<Uri?>(null)

    val isDuplicate = MutableLiveData<Int>(null)
    val image = MutableLiveData<String>(null)

    val fcmToken = MutableLiveData<String?>(null)

    private val _putProfile = MutableLiveData<SetProfileEntity>()
    val putProfile get() :LiveData<SetProfileEntity> = _putProfile

    private val _kakaoData = MutableLiveData<KakaoEntity>()
    val kakaoData get() : LiveData<KakaoEntity> = _kakaoData

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

    fun kakaoLogin(requestKakaoData: RequestKakaoData) {
        viewModelScope.launch {
            kotlin.runCatching {
                kaKaoUseCase.kakaoLogin(requestKakaoData).let {
                    _kakaoData.value = it
                }
            }
        }
    }
}