package com.mument_android.app.di

import com.mument_android.data.controller.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ControllerModule {

    @Binds
    @Singleton
    abstract fun bindLikeMumentController(likeMumentDataSourceImpl: LikeMumentDataSourceImpl): LikeMumentDataSource

    @Binds
    @Singleton
    abstract fun bindRecordController(recordControllerImpl: RecordControllerImpl): RecordController

    @Binds
    @Singleton
    abstract fun bindRecordModifyController(recordModifyControllerImpl: RecordModifyControllerImpl): RecordModifyController

    @Binds
    @Singleton
    abstract fun bindDeleteMumentController(deleteMumentControllerImpl: DeleteMumentControllerImpl): DeleteMumentController

    @Binds
    @Singleton
    abstract fun bindDeleteBlockUserController(deleteBlockUserControllerImpl: DeleteBlockUserControllerImpl): DeleteBlockUserController
}