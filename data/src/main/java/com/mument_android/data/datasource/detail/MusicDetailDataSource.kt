package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.core.base.BaseResponse

interface MusicDetailDataSource {
    suspend fun fetchMusicDetailInfo(musicId: String, userId: String): BaseResponse<MusicDetailDto>
}