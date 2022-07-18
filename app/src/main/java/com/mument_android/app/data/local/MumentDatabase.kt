package com.mument_android.app.data.local.todaymument

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mument_android.app.data.datasource.home.DateTypeConverter
import com.mument_android.app.data.datasource.home.IntListTypeConverter
import com.mument_android.app.data.datasource.home.MusicTypeConverter
import com.mument_android.app.data.datasource.home.UserTypeConverter
import com.mument_android.app.data.local.recentlist.RecentSearchDAO
import com.mument_android.app.data.local.recentlist.RecentSearchData

@Database(entities = [TodayMumentEntity::class, RecentSearchData::class], version = 1)
@TypeConverters(
    value = [
        IntListTypeConverter::class,
        UserTypeConverter::class,
        MusicTypeConverter::class,
        DateTypeConverter::class
    ]
)
abstract class MumentDatabase : RoomDatabase() {

    abstract fun todayMumentDAO(): TodayMumentDAO

    abstract fun recentSearchDAO(): RecentSearchDAO
}