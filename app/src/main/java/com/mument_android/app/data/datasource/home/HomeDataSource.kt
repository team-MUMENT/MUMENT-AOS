package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.data.network.base.BaseResponse

interface HomeDataSource {
    suspend fun getBannerMument():BaseResponse<List<BannerMumentDto>>
    suspend fun getTodayMument(userId:String):BaseResponse<TodayMumentDto>
    suspend fun getKnownMument():BaseResponse<List<KnownMumentDto>>
    suspend fun getRandomMument():BaseResponse<List<RandomMumentDto>>
}