package com.mument_android.app.di

import com.mument_android.app.data.mapper.album.MusicInfoMapper
import com.mument_android.app.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.app.data.mapper.detail.*
import com.mument_android.app.data.mapper.locker.*
import com.mument_android.app.data.mapper.main.*
import com.mument_android.app.data.mapper.record.RecordMapper
import com.mument_android.app.data.mapper.user.UserMapper
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
    fun provideAlbumMapper(): MusicInfoMapper = MusicInfoMapper()

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
        musicInfoMapper: MusicInfoMapper,
        impressiveTagMapper: ImpressiveTagMapper,
        emotionalTagMapper: EmotionalTagMapper,
        isFirstTagMapper: IsFirstTagMapper
    ): MumentDetailMapper = MumentDetailMapper(userMapper, musicInfoMapper, impressiveTagMapper, emotionalTagMapper, isFirstTagMapper)

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
        musicInfoMapper: MusicInfoMapper,
        isFirstTagMapper: IsFirstTagMapper,
        impressiveTagMapper: ImpressiveTagMapper,
        emotionalTagMapper: EmotionalTagMapper
    ): MumentSummaryDtoMapper =
        MumentSummaryDtoMapper(userMapper, musicInfoMapper, isFirstTagMapper, impressiveTagMapper, emotionalTagMapper)

    @Provides
    @Singleton
    fun provideMumentSummaryMapper(userMapper: UserMapper): MumentSummaryMapper =
        MumentSummaryMapper(userMapper)

    @Provides
    @Singleton
    fun provideMusicWithMyMumentMapper(
        musicInfoMapper: MusicInfoMapper,
        mumentSummaryMapper: MumentSummaryMapper
    ): MusicWithMyMumentMapper =
        MusicWithMyMumentMapper(musicInfoMapper, mumentSummaryMapper)

    @Provides
    @Singleton
    fun provideIntegrationTagEntityMapper(): IntegrationTagMapper = IntegrationTagMapper()

}