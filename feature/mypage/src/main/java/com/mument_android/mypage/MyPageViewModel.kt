package com.mument_android.mypage

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiResult
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.domain.entity.mypage.*
import com.mument_android.domain.entity.sign.WebViewEntity
import com.mument_android.domain.usecase.mypage.*
import com.mument_android.domain.usecase.sign.GetWebViewUseCase
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.domain.entity.mypage.NoticeListEntity
import com.mument_android.domain.entity.mypage.RequestUnregisterReasonEntity
import com.mument_android.domain.entity.sign.SetProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val fetchBlockUserUseCase: FetchBlockUserUseCase,
    private val deleteBlockUserUseCase: DeleteBlockUserUseCase,
    private val fetchNoticeListUseCase: FetchNoticeListUseCase,
    private val fetchNoticeDetailUseCase: FetchNoticeDetailUseCase,
    private val userInfoUseCase: UserInfoUseCase,
    private val getWebViewUseCase: GetWebViewUseCase,
    private val fetchUnregisterInfoUseCase: FetchUnregisterInfoUseCase,
    private val postUnregisterReasonUseCase: PostUnregisterReasonUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    //myPage
    private val _isBtnClick = MutableLiveData(false)
    val isBtnClick: LiveData<Boolean> get() = _isBtnClick

    //Profile
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _id = MutableLiveData<Int>()
    val id : LiveData<Int> get() = _id

    val alarmSetting = MutableLiveData<Boolean?>(null)


    //BlockUserManagement
    private val _blockUserList = MutableStateFlow<ApiResult<List<BlockUserEntity>>?>(null)
    val blockUserList get() = _blockUserList.asStateFlow()

    //Notice
    private val _noticeList = MutableStateFlow<ApiResult<List<NoticeListEntity>>?>(null)
    val noticeList get() = _noticeList.asStateFlow()

    private val _noticeDetail = MutableStateFlow<ApiResult<NoticeListEntity>?>(null)
    val noticeDetail get() = _noticeDetail.asStateFlow()

    //Unregister
    val isUnregisterSuccess = MutableLiveData<Boolean>()

    private val _isClickReasonChooseBox = MutableLiveData(false)
    val isClickReasonChooseBox: LiveData<Boolean> get() = _isClickReasonChooseBox

    private val _isClickReasonChoose = MutableLiveData(false)
    val isClickReasonChoose: LiveData<Boolean> get() = _isClickReasonChoose

    private val _isSelectSixthReason = MutableLiveData(false)
    val isSelectSixthReason: LiveData<Boolean> get() = _isSelectSixthReason

    private val _unregisterReasonContent = MutableLiveData("")
    val unregisterReasonContent = _unregisterReasonContent

    private val _isUnregisterAgree = MutableLiveData(false)
    val isUnregisterAgree: LiveData<Boolean> get() = _isUnregisterAgree

    private val _postLogOut = MutableLiveData<Int>(null)
    val postLogOut get() : LiveData<Int> = _postLogOut

    //userInfo
    private val _userInfo = MutableLiveData<UserInfoEntity>()
    val userInfo get(): LiveData<UserInfoEntity> = _userInfo
    private val _unregisterReasonIndex = MutableLiveData(0)
    val unregisterReasonIndex = _unregisterReasonIndex

    //web view link
    private val _getWebView = MutableLiveData<WebViewEntity>()
    val getWebView get() :LiveData<WebViewEntity> = _getWebView

    //마이페이지 뷰이동 버튼 클릭
    fun isClickBtnEvent(isBtnClick: Boolean) {
        _isBtnClick.value = isBtnClick
    }

    //차단유저관리
    fun fetchBlockUserList() {
        viewModelScope.launch {
            blockUserList.value.let {
                fetchBlockUserUseCase.invoke().onStart {
                }.catch { _blockUserList.value = ApiResult.Failure(null) }
                    .collect {
                        _blockUserList.value = ApiResult.Success(it)
                    }
            }
        }
    }

    //차단 유저 해제
    fun deleteBlockUser(userId: Int) {
        viewModelScope.launch {
            deleteBlockUserUseCase(blockedUserId = userId)
                .catch { }
                .collect {
                    fetchBlockUserList()
                }
        }
    }

    fun checkBlockUserEmpty(): Boolean {
//        return _blockUserList.value == null
        return _blockUserList.value?.data?.size == null
        Log.e("TEST", "${_blockUserList.value?.data?.size}")
        //return _blockUserList.value == null
    }


    //공지사항
    fun fetchNoticeList() {
        viewModelScope.launch {
            noticeList.value.let {
                fetchNoticeListUseCase.invoke().onStart {
                }.catch {
                    _noticeList.value = ApiResult.Failure(null)
                }.collect {
                    _noticeList.value = ApiResult.Success(it)
                }
            }
        }
    }

    fun fetchNoticeDetail(noticeId: Int) {
        viewModelScope.launch {
            noticeDetail.value.let {
                fetchNoticeDetailUseCase.invoke(noticeId).onStart {
                }.catch {
                    _noticeDetail.value = ApiResult.Failure(null)
                }.collect {
                    _noticeDetail.value = ApiResult.Success(it)
                }
            }
        }
    }

    //회원탈퇴
    fun postUnregisterReason() {
        viewModelScope.launch {
            val reasonEntity = RequestUnregisterReasonEntity(
                leaveCategoryId = unregisterReasonIndex.value ?: 0,
                reasonEtc = unregisterReasonContent.value ?: ""
            )
            postUnregisterReasonUseCase.invoke(reasonEntity).catch {
                isUnregisterSuccess.value = false
            }.collect { apiResult ->
                if (apiResult) {
                    fetchUnregisterInfoUseCase.invoke().catch {
                        isUnregisterSuccess.value = false
                    }.collect {
                        isUnregisterSuccess.value = it
                    }
                }
            }
        }
    }


    //이유 선택 박스 눌렀을 때 라디오 그룹 visibility 조절
    fun clickReasonChooseBox() {
        _isClickReasonChooseBox.value = !(_isClickReasonChooseBox.value!!)
    }

    //이유 선택 하기
    fun clickReasonChoose() {
        _isClickReasonChoose.value = true
    }

    //이유 중 '기타' 를 눌렀을 때 boolean 값
    fun clickSixthReason(checkedID: Boolean) {
        _isSelectSixthReason.value = checkedID
    }

    //탈퇴 동의
    fun clickUnregisterAgree() {
        _isUnregisterAgree.value = !(_isUnregisterAgree.value!!)
    }

    //이유선택박스 초기화
    fun initReasonChooseBox() {
        _isClickReasonChooseBox.value = false
    }

    //유저 정보
    fun userInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                userInfoUseCase.invoke().let {
                    _userInfo.value = it
                    _userId.value = userInfo.value?.userName
                    _id.value = userInfo.value?.id
                }
            }
        }
    }

    //탈퇴 이유 번호 받기
    fun getUnregisterReasonIndex(index: Int) {
        _unregisterReasonIndex.value = index
    }

    //webview link
    fun getWebView(page: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getWebViewUseCase.getWebView(page).let {
                    _getWebView.value = it
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            _postLogOut.value = logOutUseCase.logout()

        }.also {
            deleteInfo()
        }
    }

    fun deleteInfo() {
        viewModelScope.launch {
            dataStoreManager.removeAccessToken()
            dataStoreManager.removeRefreshToken()
            dataStoreManager.removeUserId()
        }
    }

}


