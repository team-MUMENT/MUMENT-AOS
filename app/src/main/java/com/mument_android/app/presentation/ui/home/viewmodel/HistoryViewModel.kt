package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.BuildConfig
import com.mument_android.app.domain.entity.history.MumentHistoryEntity
import com.mument_android.app.domain.usecase.home.GetMumentHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val useCase: GetMumentHistoryUseCase) : ViewModel() {
    private val _selectSortType = MutableStateFlow<Boolean>(true)
    val selectSortType = _selectSortType.asStateFlow()
    val musicDetailData = MutableLiveData<MumentHistoryEntity>()

    private val _musicId = MutableLiveData<String>()
    val musicId: LiveData<String> = _musicId

    fun changeMusicId(id: String) {
        _musicId.value = id
    }

    fun getHistory() {
        viewModelScope.launch {
            useCase.getMumentHistory(BuildConfig.USER_ID, musicId.value?:"").collect {
                musicDetailData.value = it
            }
        }
    }

    fun changeSortType(type: Boolean) {
        _selectSortType.value = type
    }
}