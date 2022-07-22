package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import com.mument_android.R
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.presentation.ui.detail.mument.navigator.EditMumentNavigatorProvider
import com.mument_android.app.presentation.ui.main.MainActivity
import timber.log.Timber
import javax.inject.Inject

class EditMumentNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
): EditMumentNavigatorProvider {
    override fun editMument(mumentId: String, mumentDetailEntity: MumentDetailEntity) {
        with(activity as MainActivity) {
            editMument(mumentId, mumentDetailEntity)
            binding.navBar.selectedItemId = R.id.fragment_record
        }
    }
}