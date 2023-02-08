package com.mument_android.domain.entity.record

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MumentModifyEntity(
    val content: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isFirst: Boolean,
    val isPrivate: Boolean
):Parcelable
