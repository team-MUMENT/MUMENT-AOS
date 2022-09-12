package com.mument_android.app.presentation.ui.detail.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.BuildConfig
import com.mument_android.R
import com.startup.domain.entity.TagEntity
import com.startup.domain.entity.detail.MumentSummaryEntity
import com.startup.domain.entity.music.MusicInfoEntity
import com.startup.domain.usecase.detail.FetchMumentListUseCase
import com.startup.domain.usecase.detail.FetchMusicDetailUseCase
import com.startup.domain.usecase.main.CancelLikeMumentUseCase
import com.startup.domain.usecase.main.LikeMumentUseCase
import com.mument_android.app.presentation.util.IntegrationTagMapper
import com.startup.core.util.SortTypeEnum
import com.startup.core.util.SortTypeEnum.Companion.findSortTypeTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MusicDetailViewModel @Inject constructor(
    private val integrationTagMapper: IntegrationTagMapper,
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val fetchMusicDetailUseCase: FetchMusicDetailUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase
) : ViewModel() {
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

    suspend fun mapTagList(): List<TagEntity> {
        val cardTags = mutableListOf<TagEntity>()
        myMument.value?.let { mument ->
            withContext(Dispatchers.Default) {
                val isFirst = if (mument.isFirst) R.string.tag_is_first else R.string.tag_has_heard
                cardTags.add(
                    TagEntity(
                        TagEntity.TAG_IS_FIRST,
                        isFirst,
                        if (mument.isFirst) 1 else 0
                    )
                )
                cardTags.addAll(mument.cardTag.map { tagIdx -> integrationTagMapper.map(tagIdx) })
            }
        }
        return cardTags
    }

    fun fetchMusicDetail(musicId: String) {
        viewModelScope.launch {
            fetchMusicDetailUseCase(musicId, BuildConfig.USER_ID).catch { e ->
                //Todo exception handling
            }.collect {
                _musicInfo.value = it.music
                _myMument.value = it.myMument
            }
        }
    }

    fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(
                musicId,
                BuildConfig.USER_ID,
                findSortTypeTag(selectedSort.value)
            ).catch { e ->
                //Todo exception handling
            }.collect { muments ->
                _mumentList.value = muments
            }
        }
    }

    fun likeMument(mumentId: String) {
        viewModelScope.launch {
            likeMumentUseCase(mumentId, BuildConfig.USER_ID)
                .catch { e -> //Todo exception handling
                }.collect {}
        }
    }

    fun cancelLikeMument(mumentId: String) {
        viewModelScope.launch {
            cancelLikeMumentUseCase(mumentId, BuildConfig.USER_ID)
                .catch { e -> //Todo exception handling
                }
                .collect {}
        }
    }
}