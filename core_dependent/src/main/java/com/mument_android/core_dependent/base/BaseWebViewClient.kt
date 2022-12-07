package com.mument_android.core_dependent.base

import android.webkit.WebView
import android.webkit.WebViewClient

class BaseWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (view != null && url != null)
            view.loadUrl(url)
        return true
    }
}