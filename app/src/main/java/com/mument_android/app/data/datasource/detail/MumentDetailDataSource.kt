package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.detail.MumentDetailDto

interface MumentDetailDataSource {
    suspend fun fetchMumentDetail(mumentId: String, userId: String): MumentDetailDto
}