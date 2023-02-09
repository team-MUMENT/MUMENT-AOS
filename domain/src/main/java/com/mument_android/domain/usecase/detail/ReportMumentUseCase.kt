package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.detail.ReportRequest
import kotlinx.coroutines.flow.Flow

interface ReportMumentUseCase {
    suspend fun reportMuemnt(mumentId: String, reportRequest: ReportRequest): Void?
}