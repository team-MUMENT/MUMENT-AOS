package com.mument_android.core_dependent.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object FirebaseAnalyticsUtil {

    private val firebaseAnalytics: FirebaseAnalytics?
        get() = Firebase.analytics
    fun firebaseLog(event: String, paramKey: String, paramVal: String) {
        firebaseAnalytics?.logEvent(event) {
            param(paramKey, paramVal)
        }
    }

}