package com.mument_android.data.network

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class NullOrEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter { body -> if (body.contentLength() == 0L) {
            null
        } else {
            Log.e("dfsd", "dfads")
            delegate.convert(body)
        } }
    }
}