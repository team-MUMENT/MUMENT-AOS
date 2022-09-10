package com.mument_android.app.domain.usecase.detail

import com.mument_android.app.data.controller.DeleteMumentController
//TODO data layer remove
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMumentUseCaseImpl @Inject constructor(
    private val deleteMumentController: DeleteMumentController
): DeleteMumentUseCase{
    override suspend fun invoke(mumentId: String): Flow<Unit> = deleteMumentController.deleteMument(mumentId)
}