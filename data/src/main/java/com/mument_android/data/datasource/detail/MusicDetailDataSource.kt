package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.data.util.BaseResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MusicDetailDataSource {
    suspend fun fetchMusicDetailInfo(musicId: String): Response<MusicDetailDto>
}