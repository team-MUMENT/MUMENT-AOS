package com.mument_android.app.presentation.ui.detail.mument

import android.app.Activity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.mument_android.R
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.presentation.ui.main.MainActivity
import com.mument_android.app.presentation.ui.record.RecordFragment
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