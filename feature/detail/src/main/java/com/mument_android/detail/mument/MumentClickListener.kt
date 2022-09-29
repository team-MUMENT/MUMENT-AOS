package com.mument_android.detail.mument

interface MumentClickListener {
    fun showMumentDetail(mumentId: String)
    fun likeMument(mumentId: String)
    fun cancelLikeMument(mumentId: String)
}