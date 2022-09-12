package com.startup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.startup.data.local.converter.DateTypeConverter
import com.startup.data.local.converter.IntListTypeConverter
import com.startup.data.local.recentlist.RecentSearchDAO
import com.startup.data.local.todaymument.TodayMumentDAO
import com.startup.domain.entity.home.RecentSearchData
import com.startup.domain.entity.home.TodayMumentEntity

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