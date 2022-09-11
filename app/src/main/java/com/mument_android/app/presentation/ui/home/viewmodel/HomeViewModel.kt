package com.mument_android.app.presentation.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.BuildConfig
import com.mument_android.app.domain.entity.history.User
import com.mument_android.app.data.dto.home.*
import com.mument_android.app.domain.entity.home.TodayMumentEntity
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.domain.usecase.home.SaveTodayMumentUseCase
import com.mument_android.app.domain.usecase.home.WhenHomeEnterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val useCase: WhenHomeEnterUseCase,
    val localUseCase: SaveTodayMumentUseCase
) : ViewModel() {
    val mument = listOf<MumentCard>()
    val bannerData = MutableLiveData<List<Banner>>(listOf())
    val todayMument = MutableLiveData<TodayMument>()
    val randomMument = MutableLiveData<RandomMumentDto>(null)
    val knownMument = MutableLiveData<List<AgainMument>>(listOf())

    init {
        viewModelScope.launch {
            localUseCase.getTodayMument(BuildConfig.USER_ID).onEach { result ->
            }.catch { e ->
                /*useCase.getTodayMument(BuildConfig.USER_ID)?.catch {
                    //Todo exception handling
                }?.collect {
                    todayMument.value = it?.todayMument
                    it.let { localUseCase.saveTodayMument(entityChange(it)) }
                }*/
            }.collect {
                /*if (it.todayDate != LocalDate.now().toString()) {
                    useCase.getTodayMument(BuildConfig.USER_ID)?.catch {
                        //Todo exception handling
                    }?.collect {
                        todayMument.value = it?.todayMument
                        it.let { localUseCase.saveTodayMument(entityChange(it)) }

                    }
                } else {*/
                //todayMument.value = dtoChange(it)
                //}
            }
            useCase.getBannerMument().catch {
                //Todo exception handling
            }.collect {
                bannerData.value = it.bannerList.toMutableList()
            }
            useCase.getKnownMument().catch {
                //Todo exception handling
            }.collect {
                knownMument.value = it.againMument.toMutableList()
            }
            useCase.getRandomMument().catch {
                //Todo exception handling
            }.collect {
                randomMument.value = it
            }
            useCase.getTodayMument(BuildConfig.USER_ID)?.catch {
                //Todo exception handling
            }?.collect {
                todayMument.value = it?.todayMument
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