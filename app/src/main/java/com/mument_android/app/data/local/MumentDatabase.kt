package com.mument_android.app.data.local.todaymument

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mument_android.app.data.local.converter.DateTypeConverter
import com.mument_android.app.data.local.converter.IntListTypeConverter
import com.mument_android.app.data.local.converter.MusicTypeConverter
import com.mument_android.app.data.local.converter.UserTypeConverter
import com.mument_android.app.data.local.recentlist.RecentSearchDAO
import com.mument_android.app.data.local.recentlist.RecentSearchData

@Database(entities = [TodayMumentEntity::class, RecentSearchData::class], version = 1)
@TypeConverters(
    value = [
        IntListTypeConverter::class,
        DateTypeConverter::class
    ]
)
abstract class MumentDatabase : RoomDatabase() {

    abstract fun todayMumentDAO(): TodayMumentDAO

    abstract fun recentSearchDAO(): RecentSearchDAO
}