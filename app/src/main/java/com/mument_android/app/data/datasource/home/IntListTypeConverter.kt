package com.mument_android.app.data.datasource.home

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

@ProvidedTypeConverter
class IntListTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun listToJson(value: List<Int>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Int> {
        return gson.fromJson(value, Array<Int>::class.java).toList()
    }
}