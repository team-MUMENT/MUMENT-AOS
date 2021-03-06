package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMumentHistoryUseCaseImpl  @Inject constructor(val homeRepository: HomeRepository):GetMumentHistoryUseCase {
    override suspend fun getMumentHistory(userId: String, musicId: String): Flow<MumentHistoryDto> = homeRepository.getMumentHistory(userId, musicId)

}