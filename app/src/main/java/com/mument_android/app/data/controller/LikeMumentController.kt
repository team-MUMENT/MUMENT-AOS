package com.mument_android.app.data.controller

import com.mument_android.app.data.dto.LikeCountDto
import com.mument_android.app.data.network.base.BaseResponse

interface LikeMumentController {
    suspend fun likeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto>
    suspend fun cancelLikeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto>
}