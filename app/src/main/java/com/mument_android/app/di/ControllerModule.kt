package com.mument_android.app.di

import com.mument_android.data.controller.*
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.network.main.MainApiService
import com.mument_android.data.network.mypage.MyPageApiService
import com.mument_android.data.network.record.RecordApiService
import com.mument_android.data.network.sign.SignApiService
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
    fun provideLikeMumentController(mainApiService: MainApiService): LikeMumentDataSource =
        LikeMumentDataSourceImpl(mainApiService)

    @Provides
    @Singleton
    fun providesRecordController(
        recordApiService: RecordApiService
    ): RecordController = RecordControllerImpl(recordApiService)

    @Provides
    @Singleton
    fun provideRecordModifyController(
        recordApiService: RecordApiService
    ): RecordModifyController = RecordModifyControllerImpl(recordApiService)

    @Provides
    @Singleton
    fun provideDeleteMumentController(
        detailApiService: DetailApiService
    ): DeleteMumentController = DeleteMumentControllerImpl(detailApiService)

    @Provides
    @Singleton
    fun provideDeleteBlockUserController(
        myPageApiService: MyPageApiService
    ): DeleteBlockUserController = DeleteBlockUserControllerImpl(myPageApiService)


}