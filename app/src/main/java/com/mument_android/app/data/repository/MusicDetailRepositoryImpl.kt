package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.detail.MusicDetailDataSource
import com.mument_android.app.data.mapper.detail.MumentCardMapper
import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import com.mument_android.app.domain.repository.detail.MusicDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MusicDetailRepositoryImpl @Inject constructor(
    private val mumentCardMapper: MumentCardMapper,
    private val musicDetailDataSource: MusicDetailDataSource
): MusicDetailRepository {
    override suspend fun fetchMusicDetailInfo(musicId: String, userId: String): Flow<MumentCard> = flow {
        val response = mumentCardMapper.map(musicDetailDataSource.fetchMusicDetailInfo(musicId, userId).data)
        emit(response)
    }.flowOn(Dispatchers.IO)
}