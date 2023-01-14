package com.mument_android.core.util

import java.text.SimpleDateFormat
import java.util.Date

class DateFormatter {
    fun parseDate(date: String): Date? {
        return SimpleDateFormat(DATE_FORMAT).parse(date)
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss"
    }
}