package com.angdroid.navigation

import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.entity.record.MumentModifyEntity

interface EditMumentNavigatorProvider {
    fun editMument(
        mumentId: String,
        mumentModifyEntity: MumentModifyEntity,
        music: RecentSearchData
    )
}