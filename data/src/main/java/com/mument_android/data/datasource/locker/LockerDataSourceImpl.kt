package com.mument_android.data.datasource.locker

import com.mument_android.data.dto.locker.LockerMyMumentDto
import com.mument_android.data.network.locker.LockerApiService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class LockerDataSourceImpl @Inject constructor(
    private val lockerApiService: LockerApiService
) : LockerDataSource {

    override suspend fun fetchLockerMumumentList(tag1 : Int?, tag2: Int?, tag3: Int?): BaseResponse<LockerMyMumentDto> {
        return lockerApiService.lockerMumentList(tag1, tag2, tag3)
    }

    override suspend fun fetchLockerLikeList(tag1: Int?, tag2: Int?, tag3: Int?): BaseResponse<LockerMyMumentDto> {
        return lockerApiService.lockerLikeList(tag1, tag2, tag3)
    }
}