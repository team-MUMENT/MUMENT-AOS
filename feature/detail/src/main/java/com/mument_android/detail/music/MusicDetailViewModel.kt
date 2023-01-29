package com.mument_android.detail.music

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiStatus
import com.mument_android.core.util.DateFormatter
import com.mument_android.core_dependent.base.MviViewModel
import com.mument_android.detail.BuildConfig
import com.mument_android.detail.music.MusicDetailContract.*
import com.mument_android.detail.util.SortTypeEnum
import com.mument_android.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.domain.usecase.detail.FetchMusicDetailUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class MusicDetailViewModel @Inject constructor(
    private val fetchMumentListUseCase: FetchMumentListUseCase,
    private val fetchMusicDetailUseCase: FetchMusicDetailUseCase,
    private val likeMumentUseCase: LikeMumentUseCase,
    private val cancelLikeMumentUseCase: CancelLikeMumentUseCase,
    private val dateFormatter: DateFormatter
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
                sortMumentList(SortTypeEnum.SORT_LATEST)
            }
            MusicDetailEvent.ClickSortByLikeCount -> {
                setState { copy(mumentSortType = SortTypeEnum.SORT_LIKE_COUNT) }
                sortMumentList(SortTypeEnum.SORT_LIKE_COUNT)
            }
            is MusicDetailEvent.CheckLikeMument -> {
                likeMument(event.mumentId)
            }
            is MusicDetailEvent.UnCheckLikeMument -> {
                cancelLikeMument(event.mumentId)
            }
        }
    }

    private fun updateRequestMusicId(id: String) {
        setState { copy(requestMusicId = id) }
    }

    private fun fetchMusicDetail(musicId: String) {

        viewModelScope.launch {
            fetchMusicDetailUseCase(musicId).catch {
                setState { copy(hasError = true) }
            }.collect { result ->
                when(result) {
                    is ApiStatus.Success -> {
                        setState { copy(musicInfo = result.data?.music, myMumentInfo = result.data?.myMument) }
                    }
                    is ApiStatus.Failure -> {
                        setState { copy(hasError = true) }
                    }
                }
            }
        }
    }

    private fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(
                musicId,
                viewState.value.mumentSortType.tag
            ).catch { e ->
                setState { copy(hasError = true, mumentList = emptyList()) }
            }.collect { muments ->
                setState { copy(mumentList = muments) }
            }
        }
    }

    private fun sortMumentList(sort: SortTypeEnum) {
        val muments = viewState.value.mumentList
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val sortedMuments = if(sort == SortTypeEnum.SORT_LATEST) {
                    muments.sortedBy { dateFormatter.parseDate(it.createdAt) }
                        .reversed()
                } else {
                    muments.sortedBy { it.likeCount }
                        .reversed()
                }
                setState { copy(mumentList = sortedMuments) }
            }
        }
    }

    fun likeMument(mumentId: String) {
        viewModelScope.launch {
            likeMumentUseCase(mumentId, BuildConfig.USER_ID)
                .catch { }
                .collect { }
        }
    }

    fun cancelLikeMument(mumentId: String) {
        viewModelScope.launch {
            cancelLikeMumentUseCase(mumentId, BuildConfig.USER_ID)
                .catch { }
                .collect { }
        }
    }
}