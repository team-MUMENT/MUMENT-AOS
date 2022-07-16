package com.mument_android.app.data.datasource.home

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mument_android.app.data.local.todaymument.todaymumentdata.User

@ProvidedTypeConverter
class UserTypeConverter(private val gson: Gson) {


    @TypeConverter
    fun listToJson(value: User): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): User {
        return gson.fromJson(value, User::class.java)
    }
}