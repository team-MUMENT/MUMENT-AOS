package com.mument_android.app.domain.usecase.record

import com.mument_android.app.data.controller.RecordModifyController
import com.mument_android.app.data.dto.record.MumentRecordDto
//TODO data layer remove
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
        recordModifyController.recordModifyMument(mumentId, mumentRecordDto)
            .data?.let { emit(it.id) }
    }.flowOn(Dispatchers.IO)
}