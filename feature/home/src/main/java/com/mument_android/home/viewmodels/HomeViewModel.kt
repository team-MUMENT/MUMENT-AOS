package com.mument_android.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.util.setState
import com.mument_android.domain.entity.home.AgainMumentEntity
import com.mument_android.domain.entity.home.BannerEntity
import com.mument_android.domain.entity.home.RandomMumentEntity
import com.mument_android.domain.entity.home.TodayMumentEntity
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
    val mument = listOf<com.mument_android.domain.entity.MumentCard>()
    val bannerData = MutableStateFlow<List<BannerEntity>?>(listOf())
    val todayMument = MutableStateFlow<TodayMumentEntity?>(null)
    val randomMument = MutableStateFlow<RandomMumentEntity?>(null)
    val knownMument = MutableStateFlow<List<AgainMumentEntity>?>(listOf())
    private val _homeViewState = MutableStateFlow(HomeViewState())
    val homeViewState get() = _homeViewState.asStateFlow()

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
        viewModelScope.launch {
            localUseCase.getTodayMument(BuildConfig.USER_ID).onEach { result ->
                /*}.catch { e ->
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
                /*if (it.todayDate != LocalDate.now().toString()) {
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
                    bannerData.value = banners
                }
            }
            useCase.getKnownMument().catch {
                //Todo exception handling
            }.collect { heards ->
                if (heards != null) {
                    _homeViewState.setState {
                        copy(heardMumentEntity = heards)
                    }
                    knownMument.value = heards
                }
            }
            useCase.getRandomMument().catch {
                //Todo exception handling
            }.collect { random ->
                randomMument.value = random
                _homeViewState.setState { copy(emotionMumentEntity = random) }
            }
        }
    }

    fun bannerIndexChange(position: Int) {
        num = position
    }

}