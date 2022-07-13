package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.ResponseMumentDetailDto
import com.mument_android.app.data.network.detail.DetailApiService
import javax.inject.Inject

class MumentDetailDataSourceImpl @Inject constructor(
    private val mumentDetailApiService: DetailApiService
): MumentDetailDataSource {
    override suspend fun fetchMumentDetail(mumentId: Int): ResponseMumentDetailDto =
        mumentDetailApiService.fetchMumentDetail(mumentId)
}