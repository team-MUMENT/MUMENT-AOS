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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                likeItemMument(event.mumentId)
            }
            is MusicDetailEvent.UnCheckLikeItemMument -> {
                cancelLikeItemMument(event.mumentId)
            }
            is MusicDetailEvent.OnClickBackButton -> {
                setEffect { MusicDetailEffect.PopBackStack }
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
                        Log.e("myMumentInfo", result.data.myMument.toString())
                        setState { copy(myMumentInfo = result.data.myMument) }
                    }
                    is ApiStatus.Failure -> {
                        Log.e("myMumentInfo", result.message.toString())
                        setState { copy(hasError = true) }
                    }
                    ApiStatus.Loading -> {

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
                Log.e("error", "${e.message}")
                setState { copy(hasError = true, mumentList = emptyList()) }
            }.collect { muments ->
                Log.e("mument list", "${muments}")
                setState { copy(mumentList = muments) }
            }
        }
    }

    private fun sortMumentList(sort: SortTypeEnum) {
        val muments = viewState.value.mumentList
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val sortedMuments = if (sort == SortTypeEnum.SORT_LATEST) {
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

    fun likeItemMument(mumentId: String) {
        viewModelScope.launch {
            likeMumentUseCase(mumentId).catch {

            }.collect()
        }
    }

    fun cancelLikeItemMument(mumentId: String) {
        viewModelScope.launch {
            cancelLikeMumentUseCase(mumentId).catch { }.collect()
        }
    }

    fun likeMument(mumentId: String) {
        changeLikeStatus(true)
        viewModelScope.launch {
            likeMumentUseCase(mumentId)
                .catch {
                    changeLikeStatus(false)
                }
                .collect {
                    setEffect { MusicDetailEffect.CompleteLikeMument }
                }
        }
    }

    fun cancelLikeMument(mumentId: String) {
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