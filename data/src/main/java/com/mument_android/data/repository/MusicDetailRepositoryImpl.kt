package com.mument_android.data.repository

import com.mument_android.core.network.ApiStatus
import com.mument_android.data.datasource.detail.MusicDetailDataSource
import com.mument_android.domain.entity.detail.MusicReqeust
import com.mument_android.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.domain.entity.detail.MusicWithMyMumentEntity
import com.mument_android.domain.repository.detail.MusicDetailRepository
import com.mument_android.domain.util.ErrorHandler
import com.mument_android.domain.util.ApiStatusExtensions.toApiStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MusicDetailRepositoryImpl @Inject constructor(
    private val musicWithMyMumentMapper: MusicWithMyMumentMapper,
    private val errorHandler: ErrorHandler,
    private val musicDetailDataSource: MusicDetailDataSource
): MusicDetailRepository {
    override suspend fun fetchMusicDetailInfo(
        musicId: String,
        musicInfo: MusicReqeust
    ): Flow<ApiStatus<MusicWithMyMumentEntity>> =
        musicDetailDataSource.fetchMusicDetailInfo(musicId, musicInfo)
            .map { musicWithMyMumentMapper.map(it ?: throw NullPointerException()) }
            .toApiStatus(errorHandler)
}