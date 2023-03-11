package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.*
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(val service: HomeService) : HomeDataSource {
    override suspend fun getBannerMument(): ResultWrapper<BannerMumentDto?> =
        catchingApiCall { service.getBannerMument() }

    override suspend fun getTodayMument(): ResultWrapper<TodayMumentDto?> =
        catchingApiCall { service.getTodayMument() }


    override suspend fun getKnownMument(): ResultWrapper<KnownMumentDto?> =
        catchingApiCall { service.getKnownMument() }


    override suspend fun getRandomMument(): ResultWrapper<RandomMumentDto?> =
        catchingApiCall { service.getRandomMument() }

    override suspend fun fetchExistNotifyList(): Exist? =
        kotlin.runCatching { service.checkNewNotify().body()?.data }.getOrNull()

    override suspend fun fetchProfileExist(): Boolean? =
        kotlin.runCatching { service.checkProfileSetting().code() == 204 }.getOrNull()

}