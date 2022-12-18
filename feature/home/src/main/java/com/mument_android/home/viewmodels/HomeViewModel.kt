package com.mument_android.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.util.collectEvent
import com.mument_android.core_dependent.util.emitEffect
import com.mument_android.core_dependent.util.emitEvent
import com.mument_android.core_dependent.util.setState
import com.mument_android.domain.usecase.home.SaveTodayMumentUseCase
import com.mument_android.domain.usecase.home.WhenHomeEnterUseCase
import com.mument_android.home.BuildConfig
import com.mument_android.home.HomeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val useCase: WhenHomeEnterUseCase,
    val localUseCase: SaveTodayMumentUseCase
) : ViewModel() {
    private val _homeViewState = MutableStateFlow(HomeViewState())
    val homeViewState get() = _homeViewState.asStateFlow()

    val homeViewStateEnabled = flow {
        _homeViewState.value.run {//테스트 못함,, 데이터가 없어서
            emit(
                nullCheck(
                    bannerEntity,
                    todayMumentEntity,
                    heardMumentEntity,
                    emotionMumentEntity
                )
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), false)

    private fun nullCheck(vararg list: Any?): Boolean = list.all { it != null }


    private val _homeEffect: Channel<HomeSideEffect> = Channel()
    val effect = _homeEffect.receiveAsFlow()

    private val _homeEvent: MutableSharedFlow<HomeEvent> = MutableSharedFlow()
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
            localUseCase.getTodayMument(BuildConfig.USER_ID).onEach { result ->
                /*}.catch { e -> Data Layer로 이동
                    useCase.getTodayMument(BuildConfig.USER_ID)?.catch {
                        //Todo exception handling
                    }?.collect {
                        todayMument.value = it?.todayMument
                        it.let { localUseCase.saveTodayMument(it.todayMument) }
                    }*/
            }.collect { today ->
                _homeViewState.setState {
                    copy(todayMumentEntity = today)
                }
                /*if (it.todayDate != LocalDate.now().toString()) { Data Layer로 이동
                    useCase.getTodayMument(BuildConfig.USER_ID)?.catch {
                        //Todo exception handling
                    }?.collect { collect ->
                        todayMument.value = collect.todayMument
                        localUseCase.saveTodayMument(collect.todayMument)
                    }
                } else {
                    todayMument.value = it
                }*/
            }
            useCase.getBannerMument().catch {
                //Todo exception handling
            }.collect { banners ->
                if (banners != null) {
                    _homeViewState.setState {
                        copy(bannerEntity = banners)
                    }
                }
            }
            useCase.getKnownMument().catch {
                //Todo exception handling
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

    private fun collectEvent() {
        _homeEvent.asSharedFlow().collectEvent(viewModelScope) { event ->
            when (event) {
                HomeEvent.OnClickSearch -> emitEffect(HomeSideEffect.GoToSearchActivity)
                HomeEvent.OnClickNotification -> emitEffect(HomeSideEffect.GoToNotification)
                is HomeEvent.CallBackSearchResult -> emitEffect(
                    HomeSideEffect.NavToMusicDetail(
                        event.musicId
                    )
                )
                is HomeEvent.OnClickBanner -> emitEffect(HomeSideEffect.NavToMusicDetail(event.musicId))
                is HomeEvent.OnClickTodayMument -> emitEffect(HomeSideEffect.NavToMumentDetail(event.mument))
                is HomeEvent.OnClickHeardMument -> emitEffect(HomeSideEffect.NavToMumentDetail(event.mument))
                is HomeEvent.OnClickRandomMument -> emitEffect(
                    HomeSideEffect.NavToMumentDetail(
                        event.mument
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