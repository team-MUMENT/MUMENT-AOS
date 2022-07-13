package com.mument_android.app.data.controller

interface LikeMumentController {
    suspend fun likeMument(mumentId: Int, userId: Int)
}