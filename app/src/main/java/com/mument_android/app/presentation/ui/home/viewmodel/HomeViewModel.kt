package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.BuildConfig
import com.mument_android.app.data.dto.history.User
import com.mument_android.app.data.dto.home.*
import com.mument_android.app.data.local.todaymument.TodayMumentEntity
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.domain.usecase.home.SaveTodayMumentUseCase
import com.mument_android.app.domain.usecase.home.WhenHomeEnterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val useCase: WhenHomeEnterUseCase,
    val localUseCase: SaveTodayMumentUseCase
) : ViewModel() {
    val mument = listOf<MumentCard>()
    val bannerData = MutableLiveData<List<Banner>>(listOf())
    val todayMument = MutableLiveData<TodayMument>()
    val randomMument = MutableLiveData<List<Mument>>(listOf())
    val knownMument = MutableLiveData<List<AgainMument>>(listOf())

    init {
        viewModelScope.launch {
            localUseCase.getTodayMument(BuildConfig.USER_ID).onEach { result ->
            }.catch { e ->
                Timber.d("error ${e.message}")
                useCase.getTodayMument(BuildConfig.USER_ID).collect {
                    todayMument.value = it.todayMument
                    localUseCase.saveTodayMument(entityChange(it))
                }
            }.collect {
                if (it != null) {
                    if (it.todayDate != LocalDate.now().toString()) {
                        useCase.getTodayMument(BuildConfig.USER_ID).collect {
                            todayMument.value = it.todayMument
                            localUseCase.saveTodayMument(entityChange(it))
                        }
                    } else {
                        todayMument.value = dtoChange(it)
                    }
                } else {
                    useCase.getTodayMument(BuildConfig.USER_ID).collect {
                        todayMument.value = it.todayMument
                        localUseCase.saveTodayMument(entityChange(it))
                    }
                }
            }
            useCase.getBannerMument().collect {
                bannerData.value = it.bannerList.toMutableList()
            }
            useCase.getKnownMument().collect {
                knownMument.value = it.againMument.toMutableList()
            }
            useCase.getRandomMument().collect {
                randomMument.value = it.mumentList.toMutableList()
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

    fun entityChange(dto: TodayMumentDto): TodayMumentEntity = TodayMumentEntity(
        BuildConfig.USER_ID,
        dto.todayMument.user.name,
        dto.todayMument.user.image,
        dto.todayMument.cardTag,
        dto.todayMument._id,
        dto.todayMument.content,
        dto.todayMument.createdAt,
        dto.todayMument.feelingTag,
        dto.todayMument.impressionTag,
        dto.todayMument.displayDate,
        dto.todayMument.isFirst,
        dto.todayMument.date,
        dto.todayMument.mumentId,
        dto.todayMument.music._id,
        dto.todayMument.music.name,
        dto.todayMument.music.artist,
        dto.todayMument.music.image,
        dto.todayDate
    )

    fun dtoChange(entity: TodayMumentEntity): TodayMument = TodayMument(
        BuildConfig.USER_ID,
        entity.cardTag,
        entity.content,
        entity.createdAt,
        entity.date,
        entity.displayDate,
        entity.feelingTag,
        entity.impressionTag,
        entity.isFirst,
        entity.mumentId,
        Music(
            entity.musicId,
            entity.musicName,
            entity.musicArtist,
            entity.musicImage
        ),
        User(
            entity.userId,
            entity.userImage,
            entity.userName,
        )
    )

    fun bannerIndexChange(position: Int) {
        bannerNum = position
    }

}