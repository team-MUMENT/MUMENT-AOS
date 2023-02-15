package com.mument_android.data.datasource.detail

import com.mument_android.data.network.detail.HistoryService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall
import com.mument_android.domain.entity.history.MumentHistory
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(private val historyService: HistoryService) :
    HistoryDataSource {
    override suspend fun getMumentHistory(
        userId: String,
        musicId: String,
        default: String
    ): List<MumentHistory> =
        catchingApiCall {
            historyService.getMumentHistory(
                userId = userId,
                musicId = musicId,
                default = default
            )
        }.let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    result.data?.data?.mumentHistory ?: listOf()
                }
                else -> {
                    listOf()
                }
            }
        }
}
