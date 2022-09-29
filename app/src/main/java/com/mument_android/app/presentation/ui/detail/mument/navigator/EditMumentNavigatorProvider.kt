package com.mument_android.app.presentation.ui.detail.mument.navigator

import com.mument_android.domain.entity.detail.MumentDetailEntity

interface EditMumentNavigatorProvider {
    fun editMument(mumentId: String, mumentDetailEntity: MumentDetailEntity)
}