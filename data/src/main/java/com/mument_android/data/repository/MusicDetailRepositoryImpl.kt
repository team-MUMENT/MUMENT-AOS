package com.mument_android.data.repository

import com.mument_android.core.network.ApiStatus
import com.mument_android.data.datasource.detail.MusicDetailDataSource
import com.mument_android.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.data.util.callApi
import com.mument_android.domain.entity.detail.MusicWithMyMumentEntity
import com.mument_android.domain.repository.detail.MusicDetailRepository
import javax.inject.Inject

class MusicDetailRepositoryImpl @Inject constructor(
    private val musicWithMyMumentMapper: MusicWithMyMumentMapper,
    private val musicDetailDataSource: MusicDetailDataSource
): MusicDetailRepository {
    override suspend fun fetchMusicDetailInfo(musicId: String): ApiStatus<MusicWithMyMumentEntity> = callApi(musicWithMyMumentMapper) {
        musicDetailDataSource.fetchMusicDetailInfo(musicId)
    }
}