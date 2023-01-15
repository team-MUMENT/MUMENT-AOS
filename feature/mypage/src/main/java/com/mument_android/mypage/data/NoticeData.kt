package com.mument_android.mypage.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoticeData(
    val id: Int,
    val title: String,
    val content: String,
    val created_at: String,
) : Parcelable
