package com.mument_android.detail.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.domain.usecase.home.GetMumentHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val useCase: GetMumentHistoryUseCase) : ViewModel() {
    val selectSortType = MutableStateFlow<String>("Y")

    @OptIn(ExperimentalCoroutinesApi::class)
    val fetchHistory =
        selectSortType.flatMapLatest {
            useCase.getMumentHistory(userId = userId.value.toString(), music.value._id, it)
        }.cachedIn(viewModelScope)

    private val _music = MutableStateFlow<Music>(
        Music("", "", "", "")
    )
    val music: StateFlow<Music> = _music
    val userId: MutableStateFlow<Int> = MutableStateFlow<Int>(0)

    fun changeMusicId(music: Music) {
        _music.value = music
    }

    fun setUserId(receiveUserId: Int) {
        userId.value = receiveUserId
    }

    fun changeSortType(type: String) {
        selectSortType.value = type
    }
}