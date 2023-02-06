package com.mument_android.mypage.util

import androidx.annotation.StringRes
import com.mument_android.mypage.R

enum class UnregisterReason(val reasonIndex: Int, @StringRes val reason: Int) {
    ONE(2, R.string.unregister_reason_first),
    TWO(3, R.string.unregister_reason_second),
    THREE(4, R.string.unregister_reason_third),
    FOUR(5, R.string.unregister_reason_fourth),
    FIVE(6, R.string.unregister_reason_fifth),
    SIX(7, R.string.unregister_reason_sixth);

    companion object {
        fun findUnregisterReason(reasonIndex: Int): Int {
            return values().find { it.reasonIndex == reasonIndex }?.reasonIndex
                ?: throw IllegalArgumentException("Cannot find Unregister reason...")
        }

    }
}