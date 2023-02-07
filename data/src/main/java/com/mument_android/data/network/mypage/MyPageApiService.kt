package com.mument_android.data.network.mypage

import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.data.dto.mypage.UserInfoDto
import com.mument_android.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/mument/notice/{noticeId}")
    suspend fun fetchNoticeDetail(
        @Path("noticeId") noticeId : Int
    ):BaseResponse<NoticeListDto>

    @GET("/user/profile")
    suspend fun getUserInfo() : BaseResponse<UserInfoDto>

}