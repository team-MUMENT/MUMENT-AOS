package com.mument_android.app.data.datasource.locker

import com.mument_android.app.data.dto.ResponseMyMumentListDto

interface LockerDataSource {
    suspend fun fetchLockerMumumentList(): ResponseMyMumentListDto
}