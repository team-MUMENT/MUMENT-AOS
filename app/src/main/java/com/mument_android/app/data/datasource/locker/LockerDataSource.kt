package com.mument_android.app.data.datasource.locker

import com.mument_android.app.data.dto.MyMumentListDto
import com.mument_android.app.data.dto.locker.LockerMyMumentDto
import com.mument_android.app.data.network.base.BaseResponse

interface LockerDataSource {
    suspend fun fetchLockerMumumentList(userId: String, tag1 : Int, tag2: Int, tag3: Int): BaseResponse<LockerMyMumentDto>
}