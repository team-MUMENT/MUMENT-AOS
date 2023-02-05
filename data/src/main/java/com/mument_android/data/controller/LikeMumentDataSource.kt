package com.mument_android.data.controller

import com.mument_android.data.dto.LikeCountDto
import com.mument_android.data.util.BaseResponse

interface LikeMumentDataSource {
    suspend fun likeMument(mumentId: String): BaseResponse<LikeCountDto>
    suspend fun cancelLikeMument(mumentId: String): BaseResponse<LikeCountDto>
}