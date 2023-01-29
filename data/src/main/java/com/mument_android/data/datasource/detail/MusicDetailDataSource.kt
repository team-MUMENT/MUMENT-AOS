package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.data.util.BaseResponse

interface MusicDetailDataSource {
    suspend fun fetchMusicDetailInfo(musicId: String): BaseResponse<MusicDetailDto>
}