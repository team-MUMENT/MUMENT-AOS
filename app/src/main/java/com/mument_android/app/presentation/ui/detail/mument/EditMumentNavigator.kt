package com.mument_android.app.presentation.ui.detail.mument

import com.mument_android.app.domain.entity.detail.MumentDetailEntity

interface EditMumentNavigator {
    fun editMument(mumentId: String, mumentDetailEntity: MumentDetailEntity)
}