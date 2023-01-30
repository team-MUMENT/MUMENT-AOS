package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.BlockUserEntity
import kotlinx.coroutines.flow.Flow

interface FetchBlockUserUseCase {
    suspend operator fun invoke():Flow<List<BlockUserEntity>>
}