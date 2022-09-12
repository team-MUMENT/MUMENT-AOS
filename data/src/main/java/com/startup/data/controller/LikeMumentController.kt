package com.startup.data.controller

import com.startup.data.dto.LikeCountDto
import com.startup.core.base.BaseResponse

interface LikeMumentController {
    suspend fun likeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto>
    suspend fun cancelLikeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto>
}