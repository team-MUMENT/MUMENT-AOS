package com.mument_android.app.presentation.ui.detail.mument

interface MumentClickListener {
    fun showMumentDetail(mumentId: String)
    fun likeMument(mumentId: String)
    fun cancelLikeMument(mumentId: String)
}