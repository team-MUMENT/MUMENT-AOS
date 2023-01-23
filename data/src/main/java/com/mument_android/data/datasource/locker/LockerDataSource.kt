package com.mument_android.data.datasource.locker

import com.mument_android.data.dto.locker.LockerMyMumentDto
import com.mument_android.data.util.BaseResponse

interface LockerDataSource {
    suspend fun fetchLockerMumumentList(tag1 : Int?, tag2: Int?, tag3: Int?): BaseResponse<LockerMyMumentDto>

    suspend fun fetchLockerLikeList(tag1: Int?, tag2: Int?, tag3: Int?) : BaseResponse<LockerMyMumentDto>
}