package com.startup.data.datasource.detail

import com.startup.data.dto.detail.MusicDetailDto
import com.startup.core.base.BaseResponse

interface MusicDetailDataSource {
    suspend fun fetchMusicDetailInfo(musicId: String, userId: String): BaseResponse<MusicDetailDto>
}