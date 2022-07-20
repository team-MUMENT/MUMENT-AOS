package com.mument_android.app.domain.usecase.detail

import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import kotlinx.coroutines.flow.Flow


interface FetchMumentListUseCase {
    suspend operator fun invoke(musicId: String, userId: String, default: String): Flow<List<MumentDetailEntity>>
}