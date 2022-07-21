package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.detail.MusicDetailDto
import com.mument_android.app.data.network.base.BaseResponse

interface MusicDetailDataSource {
    suspend fun fetchMusicDetailInfo(musicId: String, userId: String): BaseResponse<MusicDetailDto>
}