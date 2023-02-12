package com.mument_android.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.util.collectEvent
import com.mument_android.core_dependent.util.emitEffect
import com.mument_android.core_dependent.util.emitEvent
import com.mument_android.core_dependent.util.setState
import com.mument_android.domain.usecase.home.WhenHomeEnterUseCase
import com.mument_android.home.main.HomeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: WhenHomeEnterUseCase
) : ViewModel() {
    private val _homeViewState = MutableStateFlow(HomeViewState())
    val homeViewState get() = _homeViewState.asStateFlow()
    private val _homeEffect: Channel<HomeSideEffect> = Channel()
    val effect = _homeEffect.receiveAsFlow()
    private val _homeEvent: MutableSharedFlow<HomeEvent> = MutableSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeViewStateEnabled =
        _homeViewState.flatMapLatest { //테스트 못함,, 데이터가 없어서
            with(it) {
                flow {
                    emit(
                        nullCheck(
                            bannerEntity,
                            todayMumentEntity,
                            heardMumentEntity,
                            emotionMumentEntity
                        )
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = false
        )

    private fun nullCheck(vararg list: Any?): Boolean = list.all { it != null }
    var num = 0

    val bannerNumIncrease = flow {
        while (true) {
            delay(2000)
            emit(num++)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), 0)


    init {
        collectEvent()
        viewModelScope.launch {
            useCase.getTodayMument().catch {
            }.collect { today ->
                _homeViewState.setState {
                    copy(todayMumentEntity = today)
                }
            }
            useCase.getBannerMument().catch {
            }.collect { banners ->
                if (banners != null) {
                    _homeViewState.setState {
                        copy(bannerEntity = banners)
                    }
                }
            }
            useCase.getKnownMument().catch {
            }.collect { heards ->
                if (heards != null) {
                    _homeViewState.setState {
                        copy(heardMumentEntity = heards)
                    }
                }
            }
            useCase.getRandomMument().catch {
                //Todo exception handling
            }.collect { random ->
                if (random != null) {
                    _homeViewState.setState { copy(emotionMumentEntity = random) }
                }
            }
        }
    }

    fun checkNotifyExist() {
        viewModelScope.launch {
            bannerNumIncrease.collectLatest {
                useCase.checkNotifyExist().catch { }.collect { result ->
                    _homeViewState.setState { copy(notificationStatus = result ?: false) }
                }
            }
        }
    }

    private fun collectEvent() {
        _homeEvent.asSharedFlow().collectEvent(viewModelScope) { event ->
            when (event) {
                HomeEvent.OnClickSearch -> emitEffect(HomeSideEffect.GoToSearchActivity)
                HomeEvent.OnClickNotification -> emitEffect(HomeSideEffect.GoToNotification)
                is HomeEvent.CallBackSearchResult -> emitEffect(
                    HomeSideEffect.NavToMusicDetail(event.musicInfo)
                )
                is HomeEvent.OnClickBanner -> emitEffect(HomeSideEffect.NavToMusicDetail(event.musicInfo))
                is HomeEvent.OnClickTodayMument -> emitEffect(
                    HomeSideEffect.NavToMumentDetail(
                        event.mument,
                        event.musicInfo
                    )
                )
                is HomeEvent.OnClickHeardMument -> emitEffect(
                    HomeSideEffect.NavToMumentDetail(
                        event.mument,
                        event.musicInfo
                    )
                )
                is HomeEvent.OnClickRandomMument -> emitEffect(
                    HomeSideEffect.NavToMumentDetail(
                        event.mument,
                        event.musicInfo
                    )
                )
            }
        }
    }

    fun emitEvent(event: HomeEvent) {
        _homeEvent.emitEvent(viewModelScope, event)
    }

    private fun emitEffect(effect: HomeSideEffect) {
        _homeEffect.emitEffect(viewModelScope) { effect }
    }

    fun bannerIndexChange(position: Int) {
        num = position
    }

}