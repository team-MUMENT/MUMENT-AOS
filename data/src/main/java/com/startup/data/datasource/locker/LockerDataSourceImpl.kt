package com.startup.data.datasource.locker

import com.startup.data.dto.locker.LockerMyMumentDto
import com.startup.data.network.locker.LockerApiService
import com.startup.core.base.BaseResponse
import javax.inject.Inject

class LockerDataSourceImpl @Inject constructor(
    private val lockerApiService: LockerApiService
) : LockerDataSource {

    override suspend fun fetchLockerMumumentList(userId: String, tag1 : Int?, tag2: Int?, tag3: Int?): BaseResponse<LockerMyMumentDto> {
        return lockerApiService.lockerMumentList(userId, tag1, tag2, tag3)
    }

    override suspend fun fetchLockerLikeList(userId: String, tag1: Int?, tag2: Int?, tag3: Int?): BaseResponse<LockerMyMumentDto> {
        return lockerApiService.lockerLikeList(userId, tag1, tag2, tag3)
    }
}