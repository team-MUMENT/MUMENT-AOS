package com.mument_android.data.repository

import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.sign.SignMapper
import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.repository.sign.SignRepository
import timber.log.Timber
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signMapper: SignMapper,
    private val signDataSource: SignDataSource
): SignRepository {

    override suspend fun signDupCheck(profileId: String) : Int {
        signDataSource.signDupCheck(profileId).let {
           return it.code()
        }
    }
}