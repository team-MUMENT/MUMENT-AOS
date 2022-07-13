package com.mument_android.app.domain.usecase.main

import com.mument_android.app.data.controller.LikeMumentController
import javax.inject.Inject

class LikeMumentUseCaseImpl @Inject constructor(
    private val likeMumentController: LikeMumentController
): LikeMumentUseCase {
    override suspend fun invoke(mumentId: Int, userId: Int) = likeMumentController.likeMument(mumentId, userId)
}