package com.mument_android.app.di

import com.mument_android.data.mapper.album.MusicInfoMapper
import com.mument_android.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.data.mapper.detail.*
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.data.mapper.locker.LockerMapper
import com.mument_android.data.mapper.locker.MumentLockerCardMapper
import com.mument_android.data.mapper.main.EmotionalTagMapper
import com.mument_android.data.mapper.main.ImpressiveTagMapper
import com.mument_android.data.mapper.main.IsFirstTagMapper
import com.mument_android.data.mapper.record.MumentRecordMapper
import com.mument_android.data.mapper.record.RecordMapper
import com.mument_android.data.mapper.user.UserMapper
import com.mument_android.data.mapper.home.HomeTodayMumentMapper
import com.mument_android.data.mapper.home.RecentSearchDataMapper
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
    fun provideHomeTodayMumentMapper(): HomeTodayMumentMapper = HomeTodayMumentMapper()

    @Provides
    @Singleton
    fun provideRecentSearchDataMapper(): RecentSearchDataMapper = RecentSearchDataMapper()

    @Provides
    @Singleton
    fun provideMumentDetailMapper(
        userMapper: UserMapper,
        impressiveTagMapper: ImpressiveTagMapper,
        emotionalTagMapper: EmotionalTagMapper,
        isFirstTagMapper: IsFirstTagMapper
    ): MumentDetailMapper = MumentDetailMapper(
        userMapper,
        impressiveTagMapper,
        emotionalTagMapper,
        isFirstTagMapper
    )

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
        MumentSummaryDtoMapper(
            userMapper,
            musicInfoMapper,
            isFirstTagMapper,
            impressiveTagMapper,
            emotionalTagMapper
        )

    @Provides
    @Singleton
    fun provideIntegrationTagMapper(): IntegrationTagMapper = IntegrationTagMapper()

    @Provides
    @Singleton
    fun provideMumentSummaryMapper(userMapper: UserMapper, integrationTagMapper: IntegrationTagMapper): MumentSummaryMapper =
        MumentSummaryMapper(userMapper, integrationTagMapper)

    @Provides
    @Singleton
    fun provideMusicWithMyMumentMapper(
        musicInfoMapper: MusicInfoMapper,
        mumentSummaryMapper: MumentSummaryMapper
    ): MusicWithMyMumentMapper =
        MusicWithMyMumentMapper(musicInfoMapper, mumentSummaryMapper)

    @Provides
    @Singleton
    fun provideBlockUserMapper(): BlockUserMapper = BlockUserMapper()

}