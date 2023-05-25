package com.mument_android.detail.mument.listener

interface MumentClickListener {
    fun showMumentDetail(mumentId: String)
    fun likeMument(mumentId: String, resultCallback: (Boolean) -> Unit)
    fun cancelLikeMument(mumentId: String, resultCallback: (Boolean) -> Unit)
}