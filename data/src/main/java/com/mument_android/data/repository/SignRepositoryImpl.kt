package com.mument_android.data.repository

import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.sign.SignMapper
import com.mument_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signMapper: SignMapper,
    private val signDataSource: SignDataSource
): SignRepository {

    override suspend fun signDupCheck(profileId: String) {
        signDataSource.signDupCheck(profileId).data?.let {
            signMapper.map(it)
        }
    }
}