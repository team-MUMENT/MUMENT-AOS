package com.mument_android.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mument_android.data.local.MumentDatabase
import com.mument_android.data.local.converter.DateTypeConverter
import com.mument_android.data.local.converter.IntListTypeConverter
import com.mument_android.data.local.recentlist.RecentSearchDAO
import com.mument_android.data.local.todaymument.TodayMumentDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, gson: Gson): MumentDatabase {
        return Room.databaseBuilder(
            context,
            MumentDatabase::class.java,
            "friend_data_database"
        )
            .addTypeConverter(IntListTypeConverter(gson))
            .addTypeConverter(DateTypeConverter())
            .build()
    }

    @Singleton
    @Provides
    fun provideTodayDao(database: MumentDatabase): TodayMumentDAO {
        return database.todayMumentDAO()
    }

    @Singleton
    @Provides
    fun provideRecentDao(database: MumentDatabase): RecentSearchDAO {
        return database.recentSearchDAO()
    }
}