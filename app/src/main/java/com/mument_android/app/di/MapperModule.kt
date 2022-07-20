package com.mument_android.app.di

import com.mument_android.app.data.mapper.album.AlbumMapper
import com.mument_android.app.data.mapper.detail.MumentCardMapper
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.data.mapper.detail.MumentSummaryDtoMapper
import com.mument_android.app.data.mapper.locker.LockerMapper
import com.mument_android.app.data.mapper.locker.MumentLockerCardMapper
import com.mument_android.app.data.mapper.main.EmotionalTagMapper
import com.mument_android.app.data.mapper.main.ImpressiveTagMapper
import com.mument_android.app.data.mapper.main.IsFirstTagMapper
import com.mument_android.app.data.mapper.record.RecordMapper
import com.mument_android.app.data.mapper.user.UserMapper
import com.mument_android.app.domain.entity.MumentCard
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper = UserMapper()

    @Provides
    @Singleton
    fun provideAlbumMapper(): AlbumMapper = AlbumMapper()

    @Provides
    @Singleton
    fun provideImpressiveMapper(): ImpressiveTagMapper = ImpressiveTagMapper()

    @Provides
    @Singleton
    fun provideEmotionalTagMapper(): EmotionalTagMapper = EmotionalTagMapper()

    @Provides
    @Singleton
    fun provideIsFirstTagMapper(): IsFirstTagMapper = IsFirstTagMapper()

    @Provides
    @Singleton
    fun provideRecordMapper(): RecordMapper = RecordMapper()

    @Provides
    @Singleton
    fun provideMumentDetailMapper(
        userMapper: UserMapper,
        albumMapper: AlbumMapper,
        impressiveTagMapper: ImpressiveTagMapper,
        emotionalTagMapper: EmotionalTagMapper,
        isFirstTagMapper: IsFirstTagMapper
    ): MumentDetailMapper = MumentDetailMapper(userMapper, albumMapper, impressiveTagMapper, emotionalTagMapper, isFirstTagMapper)

    @Provides
    @Singleton
    fun provideMumentLockerCardMapper(): MumentLockerCardMapper = MumentLockerCardMapper()

    @Provides
    @Singleton
    fun provideLockerMumentListMapper(mumentLockerCardMapper: MumentLockerCardMapper): LockerMapper =
        LockerMapper(mumentLockerCardMapper)


    @Provides
    @Singleton
    fun provideMumentCardMapper(): MumentCardMapper = MumentCardMapper()

    @Provides
    @Singleton
    fun provideMumentSummaryDtoMapper(
        userMapper: UserMapper,
        albumMapper: AlbumMapper,
        isFirstTagMapper: IsFirstTagMapper,
        impressiveTagMapper: ImpressiveTagMapper,
        emotionalTagMapper: EmotionalTagMapper
    ): MumentSummaryDtoMapper = MumentSummaryDtoMapper(userMapper, albumMapper, isFirstTagMapper, impressiveTagMapper, emotionalTagMapper)
}