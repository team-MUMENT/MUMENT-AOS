package com.mument_android.domain.usecase.home

import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BeforeWhenHomeEnterUseCaseImpl @Inject constructor(private val homeRepository: HomeRepository) :
    BeforeWhenHomeEnterUseCase {

    override suspend fun checkProfileExist(): Flow<Boolean?> =
        flow { emit(homeRepository.fetchProfileExist()) }
}