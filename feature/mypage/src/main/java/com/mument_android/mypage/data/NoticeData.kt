package com.mument_android.mypage.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class NoticeData(
    val id: Int,
    val title: String,
    val content: String,
    val created_at: String,
) : Parcelable
