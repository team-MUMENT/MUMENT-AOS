package com.mument_android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mument_android.data.local.converter.DateTypeConverter
import com.mument_android.data.local.converter.IntListTypeConverter
import com.mument_android.data.local.recentlist.RecentSearchDAO
import com.mument_android.data.local.todaymument.TodayMumentDAO
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.entity.home.TodayMumentEntity

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