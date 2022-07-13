package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.ResponseMumentDetailDto

interface MumentDetailDataSource {
    suspend fun fetchMumentDetail(mumentId: Int, userId: Int): ResponseMumentDetailDto
}