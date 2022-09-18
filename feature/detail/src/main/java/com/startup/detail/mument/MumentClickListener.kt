package com.startup.detail.mument

interface MumentClickListener {
    fun showMumentDetail(mumentId: String)
    fun likeMument(mumentId: String)
    fun cancelLikeMument(mumentId: String)
}