package com.mument_android.home.util

import androidx.annotation.DrawableRes
import com.mument_android.home.R

enum class NotifyType(@DrawableRes val res: Int) {
    LIKE(R.drawable.ic_heart_small), NOTICE(R.drawable.ic_mument_noti_small), MARKETING(R.drawable.ic_mument_noti_small)
}