package com.mument_android.app.data.datasource.home

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*


@ProvidedTypeConverter
class DateTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun jsonToList(value: Date?): Long? {
        return value?.time?.toLong()
    }
}