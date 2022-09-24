package com.startup.home.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.startup.domain.entity.MumentCard
import com.startup.domain.entity.home.AgainMumentEntity
import com.startup.domain.entity.home.BannerEntity
import com.startup.domain.entity.home.RandomMumentEntity
import com.startup.domain.entity.home.TodayMumentEntity
import com.startup.domain.usecase.home.SaveTodayMumentUseCase
import com.startup.domain.usecase.home.WhenHomeEnterUseCase
import com.startup.home.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val useCase: WhenHomeEnterUseCase,
    val localUseCase: SaveTodayMumentUseCase
) : ViewModel() {
    val mument = listOf<MumentCard>()
    val bannerData = MutableLiveData<List<BannerEntity>>(listOf())
    val todayMument = MutableLiveData<TodayMumentEntity>()
    val randomMument = MutableLiveData<RandomMumentEntity>(null)
    val knownMument = MutableLiveData<List<AgainMumentEntity>>(listOf())

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
                bannerData.value = it?.toMutableList()
            }
            useCase.getKnownMument().catch {
                //Todo exception handling
            }.collect {
                knownMument.value = it?.toMutableList()
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

    private var bannerNum = 0
    val bannerNumIncrease = flow {
        while (true) {
            bannerNum = ++bannerNum
            delay(3000)
            emit(bannerNum)
        }
    }

    fun bannerIndexChange(position: Int) {
        bannerNum = position
    }

}