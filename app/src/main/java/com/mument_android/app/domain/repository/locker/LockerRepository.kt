package com.mument_android.app.domain.repository.locker

import com.mument_android.app.domain.entity.LockerMumentEntity

interface LockerRepository {
    suspend fun fetchLockerMumentList(): List<LockerMumentEntity>
}