package com.mument_android.data.repository

import com.mument_android.data.datasource.detail.MusicDetailDataSource
import com.mument_android.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.domain.entity.detail.MusicWithMyMumentEntity
import com.mument_android.domain.repository.detail.MusicDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MusicDetailRepositoryImpl @Inject constructor(
    private val musicWithMyMumentMapper: MusicWithMyMumentMapper,
    private val musicDetailDataSource: MusicDetailDataSource
): MusicDetailRepository {
    override suspend fun fetchMusicDetailInfo(musicId: String): Flow<MusicWithMyMumentEntity> = flow {
        musicDetailDataSource.fetchMusicDetailInfo(musicId).data?.let {
            emit(musicWithMyMumentMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)
}