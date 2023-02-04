package com.mument_android.data.controller

import retrofit2.Response


interface DeleteBlockUserController {
    suspend fun deleteBlockUser(blockedUserId: Int): Response<Unit>
}