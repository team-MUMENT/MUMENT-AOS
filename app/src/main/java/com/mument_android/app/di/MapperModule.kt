package com.mument_android.app.di

import com.mument_android.app.data.mapper.album.AlbumMapper
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
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
    fun provideAlbumMapper(): AlbumMapper = AlbumMapper()

    @Provides
    @Singleton
    fun provideMumentDetailMapper(
        userMapper: UserMapper,
        albumMapper: AlbumMapper
    ): MumentDetailMapper = MumentDetailMapper(userMapper, albumMapper)
}