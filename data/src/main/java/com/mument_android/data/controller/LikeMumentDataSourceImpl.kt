package com.mument_android.data.controller

import com.mument_android.data.dto.LikeCountDto
import com.mument_android.data.network.main.MainApiService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class LikeMumentDataSourceImpl @Inject constructor(
    private val mainApiService: MainApiService
): LikeMumentDataSource {
    override suspend fun likeMument(mumentId: String): BaseResponse<LikeCountDto> =
        mainApiService.likeMument(mumentId)

    override suspend fun cancelLikeMument(mumentId: String): BaseResponse<LikeCountDto> =
        mainApiService.cancelLikeMument(mumentId)
}