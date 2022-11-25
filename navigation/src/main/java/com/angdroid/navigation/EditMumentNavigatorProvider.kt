package com.angdroid.navigation

import com.mument_android.domain.entity.detail.MumentDetailEntity

interface EditMumentNavigatorProvider {
    fun editMument(mumentId: String, mumentDetailEntity: MumentDetailEntity)
}