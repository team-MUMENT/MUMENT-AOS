package com.mument_android.domain.util

import com.mument_android.core.network.ErrorMessage

interface ErrorHandler {
    fun handleError(t: Throwable): ErrorMessage
}