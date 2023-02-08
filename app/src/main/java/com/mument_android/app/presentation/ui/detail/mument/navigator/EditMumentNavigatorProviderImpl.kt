package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.EditMumentNavigatorProvider
import com.mument_android.R
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.model.TagEntity
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.entity.record.MumentModifyEntity
import com.mument_android.record.RecordActivity
import javax.inject.Inject

class EditMumentNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
) : EditMumentNavigatorProvider {
    override fun editMument(
        mumentId: String,
        mumentModifyEntity: MumentModifyEntity,
        music: RecentSearchData
    ) {
        with(activity as MainActivity) {
            startActivity(Intent(activity, RecordActivity::class.java).apply {
                putExtra("MumentModifyEntity", mumentModifyEntity)
                putExtra("RecentSearchData", music)
                putExtra("MumentID", mumentId)
            })
        }
    }
}