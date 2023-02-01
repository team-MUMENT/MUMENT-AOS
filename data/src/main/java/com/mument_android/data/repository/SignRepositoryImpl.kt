package com.mument_android.data.repository

import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.sign.RequestSetProfileMapper
import com.mument_android.data.mapper.sign.SetProfileMapper
import com.mument_android.data.mapper.sign.SignMapper
import com.mument_android.domain.entity.sign.SetProfileData
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.repository.sign.SignRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource,
    private val signMapper: SignMapper,
    private val setProfileMapper: SetProfileMapper,
    private val requestSetProfileMapper: RequestSetProfileMapper
): SignRepository {

    override suspend fun signDupCheck(profileId: String) : Int {
        signDataSource.signDupCheck(profileId).let {
           return it.code()
        }
    }

    override suspend fun signSetProfile(data: SetProfileData): Flow<SetProfileEntity> = flow {
        signDataSource.signPutProfile(requestSetProfileMapper.map(data))?.let {
            emit(setProfileMapper.map(it.data!!))
        }
    }
}