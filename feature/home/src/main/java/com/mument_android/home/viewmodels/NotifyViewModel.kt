package com.mument_android.home.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiStatus
import com.mument_android.core_dependent.util.collectEvent
import com.mument_android.core_dependent.util.emitEffect
import com.mument_android.core_dependent.util.emitEvent
import com.mument_android.core_dependent.util.setState
import com.mument_android.domain.usecase.notify.FetchNotifyListDeleteUseCase
import com.mument_android.domain.usecase.notify.FetchNotifyListUseCase
import com.mument_android.domain.usecase.notify.FetchNotifyListsReadUseCase
import com.mument_android.home.models.Notify
import com.mument_android.home.notify.NotifyContract.*
import com.mument_android.home.notify.NotifyItemMapper
import com.mument_android.home.util.NotifyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val fetchNotifyListsReadUseCase: FetchNotifyListsReadUseCase,
    private val fetchNotifyListUseCase: FetchNotifyListUseCase,
    private val fetchNotifyListDeleteUseCase: FetchNotifyListDeleteUseCase,
    private val notifyItemMapper: NotifyItemMapper
) : ViewModel() {
    private val _notifyViewState: MutableStateFlow<NotifyViewState> =
        MutableStateFlow(NotifyViewState())
    val notifyViewState get() = _notifyViewState.asStateFlow()
    private val _notifyEvent: MutableSharedFlow<NotifyEvent> = MutableSharedFlow()
    private val _notifyEffect: Channel<NotifySideEffect> = Channel()
    val notifyEffect get() = _notifyEffect.receiveAsFlow()

    init {
        collectEvent()
        viewModelScope.launch {
            fetchNotifyListUseCase.invoke().map { status ->
                when (status) {
                    is ApiStatus.Success -> {
                        status.data?.let { notifyItemMapper.map(it) }
                    }
                    else -> {
                        null
                    }
                }
            }.collect { notifyList ->//[1073, 1061, 1049, 1037]
                _notifyViewState.setState { copy(notifyList = notifyList) }
                notifyList?.asSequence()?.filter { !it.isRead }?.map { it.id }
                    .let { unReadList ->
                        unReadList?.let { nonNullUnReadList ->
                            Log.e("nonNullUnReadList", nonNullUnReadList.toList().toString())
                            fetchNotifyListsReadUseCase.invoke(nonNullUnReadList.toList()).catch { }
                                .collect { result ->
                                    if (result == true) {
                                        emitEffect(NotifySideEffect.AllReadSuccess)
                                    }
                                }
                        }
                    }
            }

        }
    }

    private fun collectEvent() {
        _notifyEvent.asSharedFlow().collectEvent(viewModelScope) { event ->
            when (event) {
                NotifyEvent.OnClickBackBtn -> emitEffect(NotifySideEffect.PopBackStack)
                NotifyEvent.OnClickSettingBtn -> emitEffect(NotifySideEffect.NavToSetting)
                is NotifyEvent.OnClickNotify -> {
                    when (event.notify.type) {
                        NotifyType.LIKE -> {
                            emitEffect(NotifySideEffect.NavToMumentDetail(event.notify))
                        }
                        NotifyType.NOTICE -> {
                            emitEffect(NotifySideEffect.NavToNotice(event.notify))
                        }
                        NotifyType.MARKETING -> {
                            //Nav to Some View
                        }
                    }
                }
                is NotifyEvent.OnDeleteNotify -> {
                    emitEffect(NotifySideEffect.DeleteNotify(event.notify))
                }
            }
        }
    }

    fun deleteNotify(notify: Notify) {
        val current = _notifyViewState.value.notifyList?.toMutableList()
        viewModelScope.launch {
            fetchNotifyListDeleteUseCase.invoke(notify.id.toString()).catch { }.collect { result ->
                if (result == true && current?.remove(notify) == true) { //Modify Remote call response OK
                    _notifyViewState.setState { copy(notifyList = current) }
                }
            }
        }
    }

    fun emitEvent(event: NotifyEvent) {
        _notifyEvent.emitEvent(viewModelScope, event)
    }

    private fun emitEffect(effect: NotifySideEffect) {
        _notifyEffect.emitEffect(viewModelScope) { effect }
    }
}