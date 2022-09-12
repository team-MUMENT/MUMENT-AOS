package com.mument_android.app.domain.usecase.detail

import com.mument_android.app.data.controller.DeleteMumentController
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
//TODO data layer remove
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMumentUseCaseImpl @Inject constructor(
    private val mumentDetailRepository: MumentDetailRepository
): DeleteMumentUseCase{
    override suspend fun invoke(mumentId: String): Flow<Unit> = mumentDetailRepository.deleteMument(mumentId)
}