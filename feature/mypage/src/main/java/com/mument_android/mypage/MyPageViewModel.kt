package com.mument_android.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiResult
import com.mument_android.domain.entity.mypage.*
import com.mument_android.domain.entity.sign.WebViewEntity
import com.mument_android.domain.usecase.mypage.*
import com.mument_android.domain.usecase.sign.GetWebViewUseCase
import com.mument_android.mypage.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val fetchBlockUserUseCase: FetchBlockUserUseCase,
    private val deleteBlockUserUseCase: DeleteBlockUserUseCase,
    private val fetchNoticeListUseCase: FetchNoticeListUseCase,
    private val fetchNoticeDetailUseCase: FetchNoticeDetailUseCase,
    private val userInfoUseCase: UserInfoUseCase,
    private val getWebViewUseCase: GetWebViewUseCase,
    private val fetchUnregisterInfoUseCase: FetchUnregisterInfoUseCase,
    private val postUnregisterReasonUseCase: PostUnregisterReasonUseCase
) : ViewModel() {

    //myPage
    private val _isBtnClick = MutableLiveData(false)
    val isBtnClick: LiveData<Boolean> get() = _isBtnClick

    //Profile
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _userImg = MutableLiveData<String>()
    val userImg: LiveData<String> get() = _userImg

    private val _userNickNameContent = MutableLiveData("")
    val userNickNameContent = _userNickNameContent

    //BlockUserManagement
    private val _blockUserList = MutableStateFlow<ApiResult<List<BlockUserEntity>>?>(null)
    val blockUserList get() = _blockUserList.asStateFlow()

    //Notice
    private val _noticeList = MutableStateFlow<ApiResult<List<NoticeListEntity>>?>(null)
    val noticeList get() = _noticeList.asStateFlow()

    private val _noticeDetail = MutableStateFlow<ApiResult<NoticeListEntity>?>(null)
    val noticeDetail get() = _noticeDetail.asStateFlow()

    private val _noticeId = MutableLiveData<Int>()
    val noticeId = _noticeId

    //Unregister
    private val _unregisterInfo = MutableStateFlow<ApiResult<UnregisterEntity>?>(null)
    val unregisterInfo get() = _unregisterInfo.asStateFlow()

    private val _unregisterReason =
        MutableStateFlow<ApiResult<UnregisterReasonEntity>?>(null)
    val unregisterReason get() = _unregisterReason.asStateFlow()

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

    //프로필 유저 정보
    fun fetchUserInfo() {
        val userData = UserData(
            userID = userId.value ?: "",
            userImg = userImg.value ?: ""
        )
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
    fun fetchUnregisterInfo() {
        viewModelScope.launch {
            unregisterInfo.value.let {
                fetchUnregisterInfoUseCase.invoke().onStart {
                }.catch {
                    _unregisterInfo.value = ApiResult.Failure(null)
                }.collect {
                    _unregisterInfo.value = ApiResult.Success(it)

                }
            }
        }
    }

    fun postUnregisterReason() {
        viewModelScope.launch {
            val reasonEntity = RequestUnregisterReasonEntity(
                leaveCategoryId = unregisterReasonIndex.value ?: 0,
                reasonEtc = unregisterReasonContent.value ?: ""
            )
            unregisterReason.value.let {
                postUnregisterReasonUseCase.invoke(reasonEntity).onStart {
                }.catch {

                }.collect {
                    _unregisterReason.value = ApiResult.Success(it)
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

}


