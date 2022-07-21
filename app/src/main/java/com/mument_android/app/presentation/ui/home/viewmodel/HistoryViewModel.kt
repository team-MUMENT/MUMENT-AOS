package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.BuildConfig
import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.dto.history.MusicX
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.MyMument
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.User
import com.mument_android.app.domain.usecase.home.GetMumentHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val useCase: GetMumentHistoryUseCase) : ViewModel() {
    private val _selectSortType = MutableStateFlow<Boolean>(true)
    val selectSortType get():StateFlow<Boolean> = _selectSortType.asStateFlow()
    val musicDetailData = MutableLiveData<MumentHistoryDto>()


    fun getHistory() {
        viewModelScope.launch {

            useCase.getMumentHistory(BuildConfig.USER_ID, "62d2959e177f6e81ee8fa3de").collect {
                Timber.d("result $it")
                musicDetailData.value = it
            }

        }
    }

    fun changeSortType(type: Boolean) {
        _selectSortType.value = type
    }
}