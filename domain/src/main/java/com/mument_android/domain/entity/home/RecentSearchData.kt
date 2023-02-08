package com.mument_android.domain.entity.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class RecentSearchData(
    val _id: String,
    val artist: String,
    val image: String,
    val name: String,
    val createAt: Date?,
):Parcelable