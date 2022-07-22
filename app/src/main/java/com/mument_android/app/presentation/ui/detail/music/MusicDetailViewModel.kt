package com.mument_android.app.presentation.ui.detail.music

import androidx.lifecycle.LiveData
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
import com.mument_android.app.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.app.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MusicDetailViewModel @Inject constructor(
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val fetchMusicDetailUseCase: FetchMusicDetailUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase
): ViewModel() {
    private val _musicId = MutableLiveData<String>()
    val musicId: LiveData<String> = _musicId

    private val _musicInfo = MutableLiveData<MusicInfoEntity>()
    val musicInfo = _musicInfo

    private val _myMument = MutableStateFlow<MumentSummaryEntity?>(null)
    val myMument = _myMument.asStateFlow()

    private val _mumentList = MutableStateFlow<List<MumentSummaryEntity>>(listOf())
    val mumentList = _mumentList.asStateFlow()

    private val _selectedSort = MutableStateFlow(SortTypeEnum.SORT_LIKE_COUNT.sort)
    val selectedSort = _selectedSort.asStateFlow()

    fun changeSelectedSort(sort: String) {
        _selectedSort.value = sort
        fetchMumentList(musicId.value ?: "")
    }

    fun changeMusicId(id: String) {
        _musicId.value = id
    }

    fun fetchMusicDetail(musicId: String) {
        viewModelScope.launch {
            fetchMusicDetailUseCase(musicId, BuildConfig.USER_ID).catch { e ->
                e.printStackTrace()
            }.collect {
                _musicInfo.value = it.music
                _myMument.value = it.myMument
            }
        }
    }

    fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(musicId, BuildConfig.USER_ID, findSortTypeTag(selectedSort.value)).catch { e ->
                e.printStackTrace()
            }.collect { muments ->
                _mumentList.value = muments
            }
        }
    }

    fun likeMument(mumentId: String) {
        viewModelScope.launch {
            likeMumentUseCase(mumentId, BuildConfig.USER_ID)
                .catch { e -> e.printStackTrace() }.collect {}
        }
    }

    fun cancelLikeMument(mumentId: String) {
        viewModelScope.launch {
            cancelLikeMumentUseCase(mumentId, BuildConfig.USER_ID)
                .catch { e -> e.printStackTrace() }
                .collect {}
        }
    }
}