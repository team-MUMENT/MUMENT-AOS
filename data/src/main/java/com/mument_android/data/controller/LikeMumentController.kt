package com.mument_android.data.controller

import com.mument_android.data.dto.LikeCountDto
import com.mument_android.core.base.BaseResponse

interface LikeMumentController {
    suspend fun likeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto>
    suspend fun cancelLikeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto>
}