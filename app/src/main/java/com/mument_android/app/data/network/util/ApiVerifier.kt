package com.mument_android.app.data.network.util

import org.json.JSONObject
import retrofit2.Response

fun <T> Response<T>.verify(): T? {
    if (isSuccessful && this.code() in 200 .. 299) {
        return body()
    } else {
        val errorString = errorBody()?.string()
        val errorObject = JSONObject(errorString)
        throw Exception(errorObject.getString("message"))
    }
}