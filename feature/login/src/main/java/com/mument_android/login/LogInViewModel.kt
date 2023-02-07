package com.mument_android.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.domain.entity.sign.*
import com.mument_android.domain.usecase.sign.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val dupCheckUseCase : SignDulCheckUseCase,
    private val putProfileUseCase: SignPutProfileUseCase,
    private val kaKaoUseCase: SignKaKaoUseCase,
    private val getWebViewUseCase: GetWebViewUseCase,
    private val newTokenUseCase: NewTokenUseCase
): ViewModel() {

    val mumentNickName = MutableLiveData<String>()
    val isRightPattern = MutableLiveData<Boolean>()
    val isActive = MutableLiveData<Boolean>()
    val imageUri = MutableLiveData<Uri?>(null)

    val isDuplicate = MutableLiveData<Int>(null)
    val image = MutableLiveData<String>(null)

    val fcmToken = MutableLiveData<String?>(null)

    val accessToken = MutableLiveData<String?>(null)
    val refreshToken = MutableLiveData<String?>(null)
    val userId = MutableLiveData<Int?>(null)

    private val _putProfile = MutableLiveData<SetProfileEntity>()
    val putProfile get() :LiveData<SetProfileEntity> = _putProfile

    private val _kakaoData = MutableLiveData<KakaoEntity>()
    val kakaoData get() : LiveData<KakaoEntity> = _kakaoData

    private val _getWebView = MutableLiveData<WebViewEntity>()
    val getWebView get() :LiveData<WebViewEntity> = _getWebView

    private val _newToken = MutableLiveData<NewTokenEntity>()
    val newToken get() : LiveData<NewTokenEntity> = _newToken

    fun saveIsFirst() {
        viewModelScope.launch {
            dataStoreManager.writeIsFirst(true)
        }
    }

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
                    Log.e("refresth token 1 !#$!#@$!@#", "${it.refreshToken}")
                    saveRefreshToken(it.refreshToken ?: "")
                    saveAccessToken(it.accessToken ?: "")
                }
            }.getOrElse {
                Log.e("error", "${it.message}")
            }
        }
    }

    fun kakaoLogin(requestKakaoData: RequestKakaoData) {
        viewModelScope.launch {
            kotlin.runCatching {
                kaKaoUseCase.kakaoLogin(requestKakaoData).let {
                    _kakaoData.value = it
                    Log.e("access token", "${it?.accessToken}")
                    saveRefreshToken(it?.refreshToken ?: "")
                    saveAccessToken(it?.accessToken ?: "")
                }
            }
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        dataStoreManager.writeRefreshToken(refreshToken)
        Log.e("111111", "${refreshToken}")
    }

    suspend fun saveAccessToken(accessToken: String) {
        dataStoreManager.writeAccessToken(accessToken)

    }

    fun saveTestUserId() {
        viewModelScope.launch {
            dataStoreManager.writeUserId(userId.value.toString())
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

    fun newToken() {
        viewModelScope.launch {
            kotlin.runCatching {
                newTokenUseCase.newToken().let {
                    _newToken.value = it
                }
            }
        }
    }
}