package com.mument_android.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    private var bannerNum = 0
    val bannerNumIncrease = flow {
        while (true) {
            bannerNum = ++bannerNum
            delay(3000)
            emit(bannerNum)
        }
    }


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
            }.collect {
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
            }.collect {
                if (it != null) {
                    bannerData.value = it
                }
            }
            useCase.getKnownMument().catch {
                //Todo exception handling
            }.collect {
                if (it != null) {
                    knownMument.value = it
                }
            }
            useCase.getRandomMument().catch {
                //Todo exception handling
            }.collect {
                randomMument.value = it
            }
            useCase.getTodayMument(BuildConfig.USER_ID).catch {
                //Todo exception handling
            }.collect {
                todayMument.value = it
            }
        }
    }

    fun bannerIndexChange(position: Int) {
        bannerNum = position
    }

}