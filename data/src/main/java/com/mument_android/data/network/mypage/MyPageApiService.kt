package com.mument_android.data.network.mypage

import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.data.dto.mypage.UserInfoDto
import com.mument_android.data.dto.mypage.UnregisterDto
import com.mument_android.data.dto.mypage.*
import com.mument_android.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.*

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
    ): BaseResponse<List<NoticeListDto>>

    @GET("/mument/notice/{noticeId}")
    suspend fun fetchNoticeDetail(
        @Path("noticeId") noticeId: Int
    ): BaseResponse<NoticeListDto>

    @GET("/user/profile")
    suspend fun getUserInfo() : BaseResponse<UserInfoDto>

    @DELETE("/user")
    suspend fun fetchUnregisterInfo(
    ) :BaseResponse<UnregisterDto>

    @POST("/user/leave-category")
    suspend fun postUnregisterReason(
        @Body requestUnregisterReasonDto: RequestUnregisterReasonDto
    ): BaseResponse<UnregisterReasonDto>

    @POST("/auth/logout")
    suspend fun postLogOut() : Response<Any?>


}