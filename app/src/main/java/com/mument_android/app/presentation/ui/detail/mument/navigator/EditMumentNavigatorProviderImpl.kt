package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.EditMumentNavigatorProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUMENT_MODIFY_ENTITY
import com.mument_android.core.util.Constants.RECENT_SEARCH_DATA
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
        if (activity is MainActivity) {
            val intent = Intent(activity, RecordActivity::class.java).apply {
                putExtra(MUMENT_MODIFY_ENTITY, mumentModifyEntity)
                putExtra(RECENT_SEARCH_DATA, music)
                putExtra(MUMENT_ID, mumentId)
            }
            activity.recordMumentLauncher.launch(intent)
        }
    }
}