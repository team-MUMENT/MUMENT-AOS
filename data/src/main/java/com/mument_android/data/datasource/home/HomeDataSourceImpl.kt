package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.BannerMumentDto
import com.mument_android.data.dto.home.KnownMumentDto
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.data.dto.home.TodayMumentDto
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(val service: HomeService) : HomeDataSource {
    override suspend fun getBannerMument(): ResultWrapper<BannerMumentDto?> =
        catchingApiCall { service.getBannerMument() }

    override suspend fun getTodayMument(userId: String): Flow<ResultWrapper<TodayMumentDto?>> =
        flow {
            emit(ResultWrapper.Success(service.getTodayMument(userId).body()))
        }.catch { throwable ->
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> ResultWrapper.GenericError(throwable.code(), throwable.message)
                else -> ResultWrapper.GenericError(null, null)
            }
        }

    override suspend fun getKnownMument(): ResultWrapper<KnownMumentDto?> =
        catchingApiCall { service.getKnownMument() }


    override suspend fun getRandomMument(): ResultWrapper<RandomMumentDto?> =
        catchingApiCall { service.getRandomMument() }

}