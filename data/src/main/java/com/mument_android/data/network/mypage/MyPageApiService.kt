package com.mument_android.data.network.mypage

import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageApiService {
    @GET("/user/block")
    suspend fun fetchBlockUser(
    ): BaseResponse<List<BlockUserDto>>

    @DELETE("/user/block/{blockedUserId}")
    suspend fun deleteBlockUser(
        @Path("blockedUserId") blockedUserId: Int
    ): Response<Unit>

    @GET("/mument/notice")
    suspend fun fetchNoticeList(
    ):BaseResponse<List<NoticeListDto>>

}