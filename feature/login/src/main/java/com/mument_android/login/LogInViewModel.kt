package com.mument_android.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.domain.entity.mypage.UserInfoEntity
import com.mument_android.domain.entity.sign.*
import com.mument_android.domain.usecase.home.BeforeWhenHomeEnterUseCase
import com.mument_android.domain.usecase.sign.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val dupCheckUseCase: SignDulCheckUseCase,
    private val putProfileUseCase: SignPutProfileUseCase,
    private val kaKaoUseCase: SignKaKaoUseCase,
    private val getWebViewUseCase: GetWebViewUseCase,
    private val newTokenUseCase: NewTokenUseCase,
    private val beforeWhenHomeEnterUseCase: BeforeWhenHomeEnterUseCase
) : ViewModel() {

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
    val isSignUp = MutableLiveData<Boolean?>(null)

    private val _putProfile = MutableLiveData<SetProfileEntity>()
    val putProfile get() :LiveData<SetProfileEntity> = _putProfile

    private val _userInfo = MutableLiveData<UserInfoEntity>()
    val userInfo get() : LiveData<UserInfoEntity> = _userInfo

    private val _kakaoData = MutableLiveData<KakaoEntity>()
    val kakaoData get() : LiveData<KakaoEntity> = _kakaoData

    private val _getWebViewEntity = MutableLiveData<WebViewEntity>()
    val getWebViewEntity get() :LiveData<WebViewEntity> = _getWebViewEntity

    private val _newToken = MutableLiveData<NewTokenEntity>()
    val newToken get() : LiveData<NewTokenEntity> = _newToken

    private val _isExist = MutableStateFlow<Boolean?>(null)
    val isExist get() = _isExist.asStateFlow()

    val isSuccess = MutableStateFlow<Boolean>(false)

    fun saveIsFirst() {
        viewModelScope.launch {
            dataStoreManager.writeIsFirst(true)
        }
    }

    fun isExistProfile() {
        viewModelScope.launch {
            beforeWhenHomeEnterUseCase.checkProfileExist().catch { }.collect {
                _isExist.value = it
                Log.e("Profile Exist", it.toString())
            }
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
                    if (it != null) {
                        _putProfile.value = it
                        Log.e("viewmodel 프로필", "${_putProfile.value}")
                        Log.e("refresth token 1 !#$!#@$!@#", it.refreshToken)
                        dataStoreManager.writeRefreshToken(it.refreshToken)
                        dataStoreManager.writeAccessToken(it.accessToken)
                        isSuccess.value = true
                    }
                }
            }.getOrElse {
                Log.e("error", "${it.message}")
            }
        }
    }

    fun kakaoLogin(requestKakaoData: RequestKakaoData) {
        viewModelScope.launch {
            kotlin.runCatching {
                kaKaoUseCase.kakaoLogin(requestKakaoData).let { kakao ->
                    _kakaoData.value = kakao
                    if (kakao != null) {
                        dataStoreManager.writeUserId(kakao._id.toString())
                        Log.e("access token", kakao.accessToken)
                        dataStoreManager.writeRefreshToken(kakao.refreshToken)
                        dataStoreManager.writeAccessToken(kakao.accessToken)
                        beforeWhenHomeEnterUseCase.checkProfileExist().catch { }.collect {
                            _isExist.value = it
                            Log.e("Profile Exist", it.toString())
                        }
                    }
                }
            }
        }
    }

    fun getWebView(page: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getWebViewUseCase.getWebView(page).let {
                    _getWebViewEntity.value = it
                }
            }
        }
    }
}