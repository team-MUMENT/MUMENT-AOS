package com.mument_android.domain.usecase.sign

import kotlinx.coroutines.flow.Flow

interface SignTestUseCase {
    //빈 패키지 방지를 위한 test class
    fun getFirstLaunch(): Boolean {
        return true
    }

    suspend fun saveFirstLaunch(isFirstLaunch: Boolean) {}
}