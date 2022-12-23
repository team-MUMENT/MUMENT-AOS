package com.mument_android.detail.music

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.base.MviViewModel
import com.mument_android.detail.BuildConfig
import com.mument_android.detail.R
import com.mument_android.detail.music.MusicDetailContract.*
import com.mument_android.detail.util.IntegrationTagMapper
import com.mument_android.detail.util.SortTypeEnum
import com.mument_android.detail.util.SortTypeEnum.Companion.findSortTypeTag
import com.mument_android.domain.entity.detail.MumentSummaryEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.domain.usecase.detail.FetchMusicDetailUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
) : MviViewModel<MusicDetailEvent, MusicDetailViewState, MusicDetailEffect>() {
    override fun setInitialState(): MusicDetailViewState = MusicDetailViewState()

    override fun handleEvents(event: MusicDetailEvent) {
        when(event) {
            is MusicDetailEvent.ReceiveRequestMusicId -> {
                updateRequestMusicId(event.musicId)
                fetchMusicDetail(event.musicId)
                fetchMumentList(event.musicId)
            }
            MusicDetailEvent.ClickSortByLatest -> {
                setState { copy(mumentSortType = SortTypeEnum.SORT_LATEST) }
                fetchMumentList(viewState.value.requestMusicId)
            }
            MusicDetailEvent.ClickSortByLikeCount -> {
                setState { copy(mumentSortType = SortTypeEnum.SORT_LIKE_COUNT) }
                fetchMumentList(viewState.value.requestMusicId)
            }
        }
    }

    private fun updateRequestMusicId(id: String) {
        setState { copy(requestMusicId = id) }
    }

    fun mapTagList(): List<TagEntity> {
        val cardTags = mutableListOf<TagEntity>()
        viewState.value.myMumentInfo?.let { mument ->
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    val isFirst = if (mument.isFirst) R.string.tag_is_first else R.string.tag_has_heard
                    cardTags.add(
                        TagEntity(TagEntity.TAG_IS_FIRST, isFirst, if (mument.isFirst) 1 else 0)
                    )
                    cardTags.addAll(mument.cardTag.map { tagIdx -> integrationTagMapper.map(tagIdx) })
                }
            }
        }
        return cardTags
    }

    private fun fetchMusicDetail(musicId: String) {
        viewModelScope.launch {
            fetchMusicDetailUseCase(musicId, BuildConfig.USER_ID).catch { e ->
                //Todo exception handling
            }.collect {
                setState { copy(musicInfo = it.music, myMumentInfo = it.myMument) }
            }
        }
    }

    private fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(
                musicId,
                BuildConfig.USER_ID,
                viewState.value.mumentSortType.tag
            ).catch { e ->
                //Todo exception handling
            }.collect { muments ->
                setState { copy(mumentList = muments) }
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