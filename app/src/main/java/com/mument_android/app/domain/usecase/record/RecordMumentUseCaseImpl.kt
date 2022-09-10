package com.mument_android.app.domain.usecase.record

import com.mument_android.app.data.controller.RecordController
import com.mument_android.app.data.dto.record.MumentRecordDto
//TODO data layer remove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecordMumentUseCaseImpl @Inject constructor(
    private val recordController: RecordController
): RecordMumentUseCase {
    override suspend fun invoke(
        musicId: String,
        userId: String,
        mumentRecordDto: MumentRecordDto
    ): Flow<String> = flow {
        recordController.recordMument(musicId, userId, mumentRecordDto).data?.let {
            emit(it.id)
        }
    }.flowOn(Dispatchers.IO)
}