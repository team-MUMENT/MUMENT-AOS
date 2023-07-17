package com.mument_android.core_dependent.util

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object FirebaseAnalyticsUtil {

    private val firebaseAnalytics: FirebaseAnalytics
        get() = Firebase.analytics
    fun firebaseLog(event: String, paramKey: String, paramVal: String) {
        firebaseAnalytics.logEvent(event) {
            param(paramKey, paramVal)
        }
    }

    fun firebaseMumentDetailLog(paramVal : String) {
        firebaseAnalytics.logEvent("mument_detail_page") {
            param("type", paramVal)
        }
    }

    //글쓰기 플로팅 버튼 클릭 시 있는 뷰
    fun firebaseWritePathLog(paramVal: String) {
        firebaseAnalytics.logEvent("write_path") {
            param("type", paramVal)
        }
    }

    //뮤멘트 앱에 진입하자마자 나타는 뷰 GA
    fun firebaseFirstVisitLog(paramVal : String) {
        firebaseAnalytics.logEvent("first_visit_page") {
            param("choice", paramVal)
        }
    }

    // 커스텀 이벤트 로그(복수 파라미터)
    fun firebaseLogs(event: String, paramKey : String, paramVal : List<String>){
        val bundle = Bundle()
        for(i in paramVal){
            bundle.putString(paramKey, i)
        }
        firebaseAnalytics.logEvent(event,bundle)
    }

    //글쓰기 취소
    fun writeProcessGA(paramVal : String) {
        firebaseAnalytics.logEvent("write_process") {
            param("journey", paramVal)
        }
    }
}