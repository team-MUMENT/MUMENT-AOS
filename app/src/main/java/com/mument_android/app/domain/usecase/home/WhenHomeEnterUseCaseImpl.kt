package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class WhenHomeEnterUseCaseImpl @Inject constructor(val homeRepository: HomeRepository) :
    WhenHomeEnterUseCase {
    override suspend fun getTodayMument(userId: String): Flow<TodayMumentDto> =
        homeRepository.getRemoteTodayMument(userId)

    override suspend fun getBannerMument(): Flow<List<BannerMumentDto>> =
        homeRepository.getBannerMument()

    override suspend fun getRandomMument(): Flow<List<RandomMumentDto>> =
        homeRepository.getRandomMument()

    override suspend fun getKnownMument(): Flow<List<KnownMumentDto>> =
        homeRepository.getKnownMument()

}