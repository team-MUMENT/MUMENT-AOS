package com.mument_android.app.di

import com.startup.data.mapper.album.MusicInfoMapper
import com.startup.data.mapper.album.MusicWithMyMumentMapper
import com.startup.data.mapper.detail.MumentCardMapper
import com.startup.data.mapper.detail.MumentDetailMapper
import com.startup.data.mapper.detail.MumentSummaryDtoMapper
import com.startup.data.mapper.detail.MumentSummaryMapper
import com.startup.data.mapper.home.RandomMumentMapper
import com.startup.data.mapper.locker.LockerMapper
import com.startup.data.mapper.locker.MumentLockerCardMapper
import com.startup.data.mapper.main.EmotionalTagMapper
import com.startup.data.mapper.main.ImpressiveTagMapper
import com.startup.data.mapper.main.IsFirstTagMapper
import com.startup.data.mapper.record.MumentRecordMapper
import com.startup.data.mapper.record.RecordMapper
import com.startup.data.mapper.user.UserMapper
import com.mument_android.app.presentation.util.IntegrationTagMapper
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
    fun provideMumentRecordMapper(): MumentRecordMapper = MumentRecordMapper()

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
    fun provideRandomMumentMapper(): RandomMumentMapper = RandomMumentMapper()

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