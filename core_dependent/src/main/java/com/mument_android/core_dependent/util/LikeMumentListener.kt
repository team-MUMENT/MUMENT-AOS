package com.mument_android.core_dependent.util

interface LikeMumentListener {
    fun likeMument(mumetId: String, resultCallback: (Boolean)-> Unit)
    fun cancelLikeMument(mumetId: String, resultCallback: (Boolean)-> Unit)
}