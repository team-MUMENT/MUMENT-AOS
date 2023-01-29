package com.mument_android.domain.usecase.detail

import com.mument_android.domain.entity.detail.MumentSummaryEntity
import kotlinx.coroutines.flow.Flow


interface FetchMumentListUseCase {
    suspend operator fun invoke(musicId: String, default: String): Flow<List<MumentSummaryEntity>>
}