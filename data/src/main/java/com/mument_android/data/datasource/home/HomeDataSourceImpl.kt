package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.BannerMumentDto
import com.mument_android.data.dto.home.KnownMumentDto
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.data.dto.home.TodayMumentDto
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

    override suspend fun fetchExistNotifyList(): Boolean? =
        kotlin.runCatching { service.checkNewNotify().body()?.data?.exist }.getOrElse {
            null
        }


    override suspend fun fetchProfileExist(): Boolean? =
        kotlin.runCatching { service.checkProfileSetting().code() == 200 }.getOrElse {
            null
        }

}