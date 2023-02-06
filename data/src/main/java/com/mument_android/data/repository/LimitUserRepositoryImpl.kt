package com.mument_android.data.repository

import com.mument_android.data.datasource.app.LimitUserDataSource
import com.mument_android.data.mapper.app.LimitUserMapper
import com.mument_android.domain.entity.LimitUserEntity
import com.mument_android.domain.repository.app.LimitUserRepository
import javax.inject.Inject

class LimitUserRepositoryImpl @Inject constructor(
    private val limitUserDataSource: LimitUserDataSource,
    private val limitUserMapper: LimitUserMapper
): LimitUserRepository {
    override suspend fun limitUser(): LimitUserEntity? {
        limitUserDataSource.limitUser().let {
            return limitUserMapper.map(it)
        }
    }
}