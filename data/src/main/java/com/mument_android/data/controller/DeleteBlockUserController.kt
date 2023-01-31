package com.mument_android.data.controller

import kotlinx.coroutines.flow.Flow

interface DeleteBlockUserController {
    suspend fun deleteBlockUser(blockedUserId: String): Flow<Unit>
}