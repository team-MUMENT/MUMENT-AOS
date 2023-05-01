package com.mument_android.detail.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.domain.usecase.detail.GetMumentHistoryUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val useCase: GetMumentHistoryUseCase,
    private val likeUseCase: LikeMumentUseCase,
    private val cancelLikeUseCase: CancelLikeMumentUseCase
) : ViewModel() {
    val selectSortType = MutableStateFlow<String>("Y")

    @OptIn(ExperimentalCoroutinesApi::class)
    val fetchHistory =
        selectSortType.flatMapLatest {
            useCase.getMumentHistory(userId = userId.value.toString(), music.value._id, it)
        }

    private val _music = MutableStateFlow<Music>(
        Music("", "", "", "")
    )
    val music: StateFlow<Music> = _music
    val userId: MutableStateFlow<Int> = MutableStateFlow<Int>(0)

    fun changeMusicId(music: Music) {
        _music.value = music
    }

    fun likeMument(mumentId: String, resultCallback: (Boolean) -> Unit) {
        viewModelScope.launch {
            likeUseCase.invoke(mumentId).catch {
                resultCallback.invoke(false)
            }.collect {
                resultCallback.invoke(true)
            }
        }
    }

    fun cancelLikeMument(mumentId: String, resultCallback: (Boolean) -> Unit) {
        viewModelScope.launch {
            cancelLikeUseCase.invoke(mumentId).catch {
                resultCallback.invoke(false)
            }.collect {
                resultCallback.invoke(true)
            }
        }
    }

    fun setUserId(receiveUserId: Int) {
        userId.value = receiveUserId
    }

    fun changeSortType(type: String) {
        selectSortType.value = type
    }
}