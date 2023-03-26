package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.WebViewEntity

interface GetWebViewUseCase {
    suspend fun getWebView(page: String, os: String): WebViewEntity?
}