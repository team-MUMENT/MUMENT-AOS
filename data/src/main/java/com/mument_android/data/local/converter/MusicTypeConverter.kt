package com.mument_android.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mument_android.domain.entity.history.Music

@ProvidedTypeConverter
class MusicTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: Music): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): Music {
        return gson.fromJson(value, Music::class.java)
    }
}