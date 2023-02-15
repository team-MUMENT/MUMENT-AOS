package com.mument_android.domain.repository.mypage

interface LogOutRepository {
    suspend fun logout(): Int
}