package com.mument_android.domain.entity.record

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MumentModifyEntity(
    val content: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isFirst: Boolean,
    val isPrivate: Boolean
):Parcelable
