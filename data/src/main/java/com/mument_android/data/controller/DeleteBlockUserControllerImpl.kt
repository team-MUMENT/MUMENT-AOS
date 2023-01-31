package com.mument_android.data.controller

import com.mument_android.data.network.mypage.MyPageApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteBlockUserControllerImpl @Inject constructor(
    private val myPageApiService: MyPageApiService
) : DeleteBlockUserController {
    override suspend fun deleteBlockUser(blockedUserId: String): Flow<Unit> = flow {
        emit(myPageApiService.deleteBlockUser(blockedUserId))
    }.flowOn(Dispatchers.IO)
}