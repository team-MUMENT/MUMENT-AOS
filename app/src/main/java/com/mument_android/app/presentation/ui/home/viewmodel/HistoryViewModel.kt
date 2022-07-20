package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.dto.history.MusicX
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.MyMument
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryViewModel : ViewModel() {
    private val _selectSortType = MutableStateFlow<Boolean>(true)
    val selectSortType get():StateFlow<Boolean> = _selectSortType.asStateFlow()
    val musicDetailData = MutableLiveData<List<MumentHistoryDto>>()

    fun changeSortType(type: Boolean) {
        _selectSortType.value = type
    }
}