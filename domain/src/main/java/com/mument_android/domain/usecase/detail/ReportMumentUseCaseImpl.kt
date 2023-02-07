package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.detail.ReportRequest
import com.mument_android.domain.repository.detail.BlockUserRepository
import com.mument_android.domain.repository.detail.MumentDetailRepository
import com.mument_android.domain.repository.detail.MusicDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportMumentUseCaseImpl @Inject constructor(
    private val mumentDetailRepository: MumentDetailRepository
): ReportMumentUseCase {
    override suspend fun reportMuemnt(mumentId: String, reportRequest: ReportRequest): Void? {
        return mumentDetailRepository.reportMument(mumentId, reportRequest)
    }
}