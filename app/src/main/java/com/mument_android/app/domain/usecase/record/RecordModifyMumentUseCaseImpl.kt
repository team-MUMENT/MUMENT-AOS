package com.mument_android.app.domain.usecase.record

import com.mument_android.app.data.controller.RecordModifyController
import com.mument_android.app.data.dto.record.MumentRecordDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecordModifyMumentUseCaseImpl @Inject constructor(
    private val recordModifyController: RecordModifyController
) : RecordModifyMumentUseCase {
    override suspend fun invoke(
        mumentId: String,
        mumentRecordDto: MumentRecordDto
    ): Flow<String> = flow {
        emit(recordModifyController.recordModifyMument(mumentId, mumentRecordDto).data.id)
    }.flowOn(Dispatchers.IO)
}