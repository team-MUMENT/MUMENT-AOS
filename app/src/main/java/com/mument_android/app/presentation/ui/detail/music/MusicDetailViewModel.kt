package com.mument_android.app.presentation.ui.detail.music

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.BuildConfig
import com.mument_android.R
import com.mument_android.app.data.enumtype.SortTypeEnum
import com.mument_android.app.data.enumtype.SortTypeEnum.Companion.findSortTypeTag
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.entity.detail.MumentSummaryEntity
import com.mument_android.app.domain.entity.detail.MusicWithMyMumentEntity
import com.mument_android.app.domain.entity.music.MusicInfoEntity
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.app.domain.usecase.detail.FetchMusicDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MusicDetailViewModel @Inject constructor(
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val fetchMusicDetailUseCase: FetchMusicDetailUseCase
): ViewModel() {
    private val _musicInfo = MutableLiveData<MusicInfoEntity>()
    val musicInfo = _musicInfo

    private val _myMument = MutableStateFlow<MumentSummaryEntity?>(null)
    val myMument = _myMument.asStateFlow()

    private val _mumentList = MutableStateFlow<List<MumentSummaryEntity>>(listOf())
    val mumentList = _mumentList.asStateFlow()

    private val _selectedSort = MutableStateFlow(SortTypeEnum.SORT_LIKE_COUNT.sort)
    val selectedSort = _selectedSort.asStateFlow()

    init {
        fetchMusicDetail()
        fetchMumentList()
    }

    fun changeSelectedSort(sort: String) {
        _selectedSort.value = sort
    }

    private fun fetchMusicDetail() {
        viewModelScope.launch {
            fetchMusicDetailUseCase(
                "62d2959e177f6e81ee8fa3de",
                BuildConfig.USER_ID
            ).catch { e ->
                e.printStackTrace()
            }.collect {
                _musicInfo.value = it.music
                _myMument.value = it.myMument
            }
        }
    }

    private fun fetchMumentList() {
        viewModelScope.launch {
            fetchMumentListUseCase(
                "62d2959e177f6e81ee8fa3de",
                BuildConfig.USER_ID,
                findSortTypeTag(selectedSort.value)
            ).catch { e ->
                e.printStackTrace()
            }.collect {
                Timber.e("$it")
                _mumentList.value = it
            }
        }
    }
}