package com.mument_android.app.data.local.todaymument

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mument_android.app.data.datasource.home.IntListTypeConverter
import com.mument_android.app.data.datasource.home.MusicTypeConverter
import com.mument_android.app.data.datasource.home.UserTypeConverter

@Database(entities = [TodayMumentEntity::class], version = 1)
@TypeConverters(value = [
    IntListTypeConverter::class.java,
    UserTypeConverter::class.java,
    MusicTypeConverter::class.java
])
abstract class TodayMumentDatabase : RoomDatabase() {

    abstract fun todayMumentDAO(): TodayMumentDAO
}