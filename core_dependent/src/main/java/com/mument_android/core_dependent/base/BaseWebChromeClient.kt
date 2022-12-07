package com.mument_android.core_dependent.base

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BaseWebChromeClient : WebChromeClient() {
    private val _titleData = MutableLiveData<String>()
    val titleData: LiveData<String>
        get() = _titleData

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        if (title != null && title.isNotEmpty())
            _titleData.value = title!!
    }
}