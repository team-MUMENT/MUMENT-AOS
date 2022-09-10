package com.mument_android.app.di

import com.mument_android.app.data.controller.*
import com.mument_android.app.data.network.detail.DetailApiService
import com.mument_android.app.data.network.main.MainApiService
import com.mument_android.app.data.network.record.RecordApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ControllerModule {

    @Provides
    @Singleton
    fun provideLikeMumentController(mainApiService: MainApiService): LikeMumentController =
        LikeMumentControllerImpl(mainApiService)

    @Provides
    @Singleton
    fun providesRecordController(
        recordApiService: RecordApiService
    ): RecordController = RecordControllerImpl(recordApiService)

    @Provides
    @Singleton
    fun provideRecordModifyController(
        recordApiService: RecordApiService
    ) : RecordModifyController = RecordModifyControllerImpl(recordApiService)

    @Provides
    @Singleton
    fun provideDeleteMumentController(
        detailApiService: DetailApiService
    ): DeleteMumentController = DeleteMumentControllerImpl(detailApiService)

}