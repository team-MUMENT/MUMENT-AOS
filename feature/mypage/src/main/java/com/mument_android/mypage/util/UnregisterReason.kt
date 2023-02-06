package com.mument_android.mypage.util

import androidx.annotation.StringRes
import com.mument_android.mypage.R

enum class UnregisterReason(val reasonIndex: Int, @StringRes val reason: Int) {
    ONE(2, R.id.unregister_reason_first),
    TWO(3, R.id.unregister_reason_second),
    THREE(4, R.id.unregister_reason_third),
    FOUR(5, R.id.unregister_reason_fourth),
    FIVE(6, R.id.unregister_reason_fifth),
    SIX(7, R.id.unregister_reason_sixth)

}