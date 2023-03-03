package com.mument_android.core_dependent.util

import android.os.Bundle
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

    // 커스텀 이벤트 로그(복수 파라미터)
    fun firebaseLogs(event: String, paramKey : String, paramVal : List<String>){
        val bundle = Bundle()
        for(i in paramVal){
            bundle.putString(paramKey, i)
        }
        firebaseAnalytics?.logEvent(event,bundle)
    }

}