package com.startup.data.repository

import com.startup.data.datasource.detail.MusicDetailDataSource
import com.startup.data.mapper.album.MusicWithMyMumentMapper
import com.startup.domain.entity.detail.MusicWithMyMumentEntity
import com.startup.domain.repository.detail.MusicDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MusicDetailRepositoryImpl @Inject constructor(
    private val musicWithMyMumentMapper: MusicWithMyMumentMapper,
    private val musicDetailDataSource: MusicDetailDataSource
): MusicDetailRepository {
    override suspend fun fetchMusicDetailInfo(musicId: String, userId: String): Flow<MusicWithMyMumentEntity> = flow {
        musicDetailDataSource.fetchMusicDetailInfo(musicId, userId).data?.let {
            emit(musicWithMyMumentMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)
}