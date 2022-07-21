package com.mument_android.app.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mument_android.app.domain.entity.detail.MumentDetailEntity

class MainViewModel: ViewModel() {
    private val _mumentId = MutableLiveData<String>("")
    val mumentId: LiveData<String> = _mumentId

    private val _mumentDetailContents = MutableLiveData<MumentDetailEntity>()
    val mumentDetailContents: LiveData<MumentDetailEntity> = _mumentDetailContents

    fun changeMumentId(id: String) {
        _mumentId.value = id
    }

    fun changeMumentContents(contents: MumentDetailEntity) {
        _mumentDetailContents.value = contents
    }
}