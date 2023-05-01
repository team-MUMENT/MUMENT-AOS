package com.mument_android.detail.music

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiStatus
import com.mument_android.core.util.DateFormatter
import com.mument_android.core_dependent.base.MviViewModel
import com.mument_android.detail.music.MusicDetailContract.*
import com.mument_android.detail.util.SortTypeEnum
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.usecase.detail.FetchMumentListUseCase
import com.mument_android.domain.usecase.detail.FetchMusicDetailUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
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
        when (event) {
            is MusicDetailEvent.ReceiveRequestMusicInfo -> {
                setState { copy(musicInfo = event.music) }
                updateRequestMusicId(event.music.id)
                fetchMusicDetail(event.music.id, event.music)
                fetchMumentList(event.music.id)
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
            is MusicDetailEvent.CheckLikeItemMument -> {
                likeItemMument(event.mumentId, event.resultCallback)
            }
            is MusicDetailEvent.UnCheckLikeItemMument -> {
                cancelLikeItemMument(event.mumentId, event.resultCallback)
            }
            is MusicDetailEvent.OnClickBackButton -> {
                val startNav = viewState.value.startNav
                setEffect { MusicDetailEffect.PopBackStack(startNav) }
            }
            is MusicDetailEvent.ReceiveStartNav -> {
                Log.e("start nav viewModel", "${event.startNav}")
                setState { copy(startNav = event.startNav) }
            }
        }
    }

    private fun updateRequestMusicId(id: String) {
        setState { copy(requestMusicId = id) }
    }

    private fun fetchMusicDetail(musicId: String, music: MusicInfoEntity) {
        viewModelScope.launch {
            fetchMusicDetailUseCase(musicId, music.toMusicRequest()).collect { result ->
                when (result) {
                    is ApiStatus.Success -> {
                        setState { copy(myMumentInfo = result.data.myMument) }
                    }
                    is ApiStatus.Failure -> {
                        setState { copy(hasError = true) }
                    }
                    ApiStatus.Loading -> {}
                }
            }
        }
    }

    private fun fetchMumentList(musicId: String) {
        viewModelScope.launch {
            fetchMumentListUseCase(
                musicId,
                viewState.value.mumentSortType.tag
            ).onStart {
                setState { copy(onNetwork = true) }
            }.catch { e ->
                setState { copy(hasError = true, onNetwork = false, mumentList = emptyList()) }
            }.collect { muments ->
                setState { copy(onNetwork = false, mumentList = muments) }
            }
        }
    }

    private fun sortMumentList(sort: SortTypeEnum) {
        val muments = viewState.value.mumentList
        viewModelScope.launch {
            val sortedMuments = withContext(Dispatchers.Default) {
                if (sort == SortTypeEnum.SORT_LATEST) {
                    muments.sortedByDescending { dateFormatter.parseDate(it.createdAt) }
                } else {
                    muments.sortedByDescending { it.likeCount }
                }
            }
            setState { copy(mumentList = sortedMuments) }
        }
    }

    private fun likeItemMument(mumentId: String, resultCallback: (Boolean) -> Unit) {
        viewModelScope.launch {
            likeMumentUseCase(mumentId).catch {
                resultCallback(false)
            }.collect {
                resultCallback(true)
            }
        }
    }

    private fun cancelLikeItemMument(mumentId: String, resultCallback: (Boolean) -> Unit) {
        viewModelScope.launch {
            cancelLikeMumentUseCase(mumentId).catch {
                resultCallback(false)
            }.collect {
                resultCallback(true)
            }
        }
    }

    private fun likeMument(mumentId: String) {
        viewModelScope.launch {
            likeMumentUseCase(mumentId)
                .catch {
                    changeLikeStatus(false)
                }
                .collect {
                    delay(1000)
                    changeLikeStatus(true)
                    setEffect { MusicDetailEffect.CompleteLikeMument }
                }
        }
    }

    private fun cancelLikeMument(mumentId: String) {
        changeLikeStatus(false)
        viewModelScope.launch {
            cancelLikeMumentUseCase(mumentId)
                .catch {
                    changeLikeStatus(true)
                }
                .collect {
                    setEffect { MusicDetailEffect.CompleteLikeMument }
                }
        }
    }

    private fun changeLikeStatus(like: Boolean) {
        if (like) {
            setState {
                copy(myMumentInfo = myMumentInfo?.apply {
                    likeCount += 1
                    isLiked = true
                })
            }
        } else {
            setState {
                copy(myMumentInfo = myMumentInfo?.apply {
                    likeCount -= 1
                    isLiked = false
                })
            }
        }
    }
}