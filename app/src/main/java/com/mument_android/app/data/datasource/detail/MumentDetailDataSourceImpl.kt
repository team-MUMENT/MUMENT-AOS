package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.MusicDto
import com.mument_android.app.data.dto.detail.MumentDetailDto
import com.mument_android.app.data.dto.UserDto
import com.mument_android.app.data.network.base.BaseResponse
import com.mument_android.app.data.network.detail.DetailApiService
import javax.inject.Inject

/**
 * Todo: 서버 Instance 올라가면 api 연결하고, 더미데이터 삭제하기
 */

class MumentDetailDataSourceImpl @Inject constructor(
    private val mumentDetailApiService: DetailApiService
): MumentDetailDataSource {
    override suspend fun fetchMumentDetail(mumentId: String, userId: String): BaseResponse<MumentDetailDto> =
        mumentDetailApiService.fetchMumentDetail(mumentId, userId)
}