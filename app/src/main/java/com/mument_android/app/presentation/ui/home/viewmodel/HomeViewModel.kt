package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.BuildConfig
import com.mument_android.app.data.dto.home.*
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.usecase.home.WhenHomeEnterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val useCase: WhenHomeEnterUseCase) : ViewModel() {
    val mument = listOf<MumentCard>()
    val bannerData = MutableLiveData<List<Banner>>(listOf())
    val todayMument = MutableLiveData<TodayMumentDto>()
    val randomMument = MutableLiveData<List<Mument>>(listOf())
    val knownMument = MutableLiveData<List<AgainMument>>(listOf())

    init {
        viewModelScope.launch {
            useCase.getBannerMument().collect {
                bannerData.value = it.bannerList.toMutableList()
            }
            useCase.getKnownMument().collect {
                Timber.d("Again!!!!! ${it.againMument}")
                knownMument.value = it.againMument.toMutableList()
            }
            useCase.getRandomMument().collect {
                Timber.d("Random!!!!! ${it.mumentList}")
                randomMument.value = it.mumentList.toMutableList()
            }

            useCase.getTodayMument(BuildConfig.USER_ID).collect {
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