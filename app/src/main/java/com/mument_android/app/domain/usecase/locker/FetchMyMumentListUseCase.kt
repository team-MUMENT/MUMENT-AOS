package com.mument_android.app.domain.usecase.locker

import com.mument_android.app.domain.entity.LockerMumentEntity

interface FetchMyMumentListUseCase {
    suspend operator fun invoke(): List<LockerMumentEntity>
}