package com.mument_android.data.controller

import com.mument_android.data.network.mypage.MyPageApiService
import retrofit2.Response
import javax.inject.Inject

class DeleteBlockUserControllerImpl @Inject constructor(
    private val myPageApiService: MyPageApiService
) : DeleteBlockUserController {
    override suspend fun deleteBlockUser(blockedUserId: Int): Response<Unit> {
        return myPageApiService.deleteBlockUser(blockedUserId)
    }
}