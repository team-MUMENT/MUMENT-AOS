package com.mument_android.data.network.mypage

import com.mument_android.data.dto.mypage.BlockUserDto
import com.mument_android.data.dto.mypage.NoticeListDto
import com.mument_android.data.util.BaseResponse
import retrofit2.http.GET

interface MyPageApiService {
    @GET("/user/block")
    suspend fun fetchBlockUser(
    ): BaseResponse<List<BlockUserDto>>


    @GET("/mument/notice")
    suspend fun fetchNoticeList(
    ):BaseResponse<List<NoticeListDto>>
}