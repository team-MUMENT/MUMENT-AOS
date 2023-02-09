package com.mument_android.app.presentation.ui.detail.mument.navigator

import android.app.Activity
import android.content.Intent
import com.angdroid.navigation.ReportMumentNavigatorProvider
import com.mument_android.detail.mument.activity.DeclarMumentActivity
import javax.inject.Inject

class ReportMumentNavigatorProviderImpl @Inject constructor(
    private val activity: Activity
): ReportMumentNavigatorProvider {
    override fun mumentDetailToReportMument() {
        activity.startActivity(Intent(activity, DeclarMumentActivity::class.java))
    }
}