package com.mument_android.detail.mument.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.entity.detail.ReportRequest
import com.mument_android.domain.usecase.detail.BlockUserUseCase
import com.mument_android.domain.usecase.detail.ReportMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MumentReportViewModel @Inject constructor(
    private val reportMumentUseCase: ReportMumentUseCase,
    private val blockUserUseCase: BlockUserUseCase,
) : ViewModel() {
    val reasonLength = MutableLiveData<String>()
    val mumentId = MutableLiveData<String?>()
    val isReportMuemnt = MutableLiveData<Boolean?>()
    val isWarnUser = MutableLiveData<Boolean?>()

    fun reportMument(mumentId: String, reportRequest: ReportRequest) {
        viewModelScope.launch {
            kotlin.runCatching { reportMumentUseCase.reportMuemnt(mumentId, reportRequest) }
                .onSuccess {
                    isReportMuemnt.value = true
                }
                .onFailure {
                    it.printStackTrace()
                }

        }
    }

    fun blockUser(mumentId: String) {
        viewModelScope.launch {
            blockUserUseCase(mumentId)
                .collect {

                }
        }
    }

}