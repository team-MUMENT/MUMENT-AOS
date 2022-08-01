package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WhenHomeEnterUseCaseImpl @Inject constructor(
    val homeRepository: HomeRepository
) : WhenHomeEnterUseCase {
    override suspend fun getTodayMument(userId: String): Flow<TodayMumentDto>? =
        homeRepository.getRemoteTodayMument(userId)

    override suspend fun getBannerMument(): Flow<BannerMumentDto> =
        homeRepository.getBannerMument()

    override suspend fun getRandomMument(): Flow<RandomMumentDto> =
        homeRepository.getRandomMument()

    override suspend fun getKnownMument(): Flow<KnownMumentDto> =
        homeRepository.getKnownMument()

}