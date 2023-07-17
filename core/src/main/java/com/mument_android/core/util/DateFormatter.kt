package com.mument_android.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatter {
    fun parseDate(date: String): Date? {
        return SimpleDateFormat(DATE_FORMAT, Locale.KOREA).parse(date)
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss"
    }
}