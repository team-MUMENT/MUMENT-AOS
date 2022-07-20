package com.mument_android.app.presentation.ui.detail.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.BuildConfig
import com.mument_android.R
import com.mument_android.app.data.enumtype.SortTypeEnum
import com.mument_android.app.data.enumtype.SortTypeEnum.Companion.findSortTypeTag
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.app.domain.usecase.detail.FetchMusicDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicDetailViewModel @Inject constructor(
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val fetchMusicDetailUseCase: FetchMusicDetailUseCase
): ViewModel() {
    private val _songName = MutableStateFlow("")
    val songName = _songName.asStateFlow()

    private val _testImage = MutableStateFlow("https://cdnimg.melon.co.kr/cm2/album/images/107/10/311/10710311_20210909184021_500.jpg?6513495083f58ce168a24189a1edb874/melon/resize/282/quality/80/optimize")
    val testImage = _testImage.asStateFlow()

    private val _myMument = MutableStateFlow<MumentCard?>(null)
    val myMument = _myMument.asStateFlow()

    private val _mumentList = MutableStateFlow<List<MumentDetailEntity>>(listOf())
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
                _myMument.value = it
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
                _mumentList.value = it
            }
        }
    }
}