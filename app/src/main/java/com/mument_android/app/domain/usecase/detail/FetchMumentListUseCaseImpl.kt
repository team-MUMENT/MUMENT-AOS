package com.mument_android.app.domain.usecase.detail

import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.repository.detail.MumentListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMumentListUseCaseImpl @Inject constructor(
    private val mumentListRepository: MumentListRepository
): FetchMumentListUseCase {
    override suspend fun invoke(
        musicId: String,
        userId: String,
        default: String
    ): Flow<List<MumentDetailEntity>> =
        mumentListRepository.fetchMumentList(musicId, userId, default)
}